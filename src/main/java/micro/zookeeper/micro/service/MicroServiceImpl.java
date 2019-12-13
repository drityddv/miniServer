package micro.zookeeper.micro.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import micro.constant.MicroServiceEnum;
import micro.service.*;
import utils.NetUtil;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/12/11 3:08 PM
 */

public class MicroServiceImpl extends MicroServiceGrpc.MicroServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(MicroServiceImpl.class);
    private static final String ROOT_PATH = "/service";
    private ZooKeeper zooKeeper;
    private volatile boolean init;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private Server rpcServer;

    /**
     * 服务信息缓存 { 服务名称 -> [服务地址...] }
     */
    private Map<String, List<String>> serviceCache = new ConcurrentHashMap<>();

    public static MicroServiceImpl valueOf(ZookeeperCenter zookeeperCenter) {
        MicroServiceImpl service = new MicroServiceImpl();
        service.zooKeeper = zookeeperCenter.zooKeeper;
        return service;
    }

    public void init() {
        try {
            // root node no need for data
            delete(ROOT_PATH);
            zooKeeper.create(ROOT_PATH, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("创建root节点");
            for (MicroServiceEnum serviceEnum : MicroServiceEnum.values()) {
                String servicePath = ROOT_PATH + "/" + serviceEnum.getServerName();
                CopyOnWriteArrayList<Object> serviceList = new CopyOnWriteArrayList<>();
                serviceCache.put(serviceEnum.getServerName(), new CopyOnWriteArrayList<>());
                zooKeeper.create(servicePath, ProtoStuffUtil.serialize(serviceList), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
                logger.info("服务[{}]缓存初始化 同步zookeeper创建节点[{}]", serviceEnum.name(), servicePath);
            }
            serviceCache.get(MicroServiceEnum.zookeeper_service.getServerName()).add("172.19.0.5:2181");
            zooKeeper.setData(ROOT_PATH + "/" + MicroServiceEnum.zookeeper_service.getServerName(),
                ProtoStuffUtil.serialize(serviceCache.get(MicroServiceEnum.zookeeper_service.getServerName())), -1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        init = true;
        start();
    }

    @Override
    public void registerService(RegisteServiceRequest request,
        StreamObserver<RegisteServiceResponse> responseObserver) {
        logger.info("[{}]", request);
        boolean success = false;
        List<String> cacheServiceAddress =
            serviceCache.get(MicroServiceEnum.valueOf(request.getServiceName()).getServerName());
        String newAddress = request.getIp() + ":" + request.getPort();
        cacheServiceAddress.add(newAddress);
        synchronized (MicroServiceEnum.valueOf(request.getServiceName())) {
            try {
                java.lang.Thread.sleep(5000);
                zooKeeper.setData(ROOT_PATH + "/" + MicroServiceEnum.valueOf(request.getServiceName()).getServerName(),
                    ProtoStuffUtil.serialize(cacheServiceAddress), -1);
                success = true;
                logger.info("新服务[{}]注册,同步缓存与zookeeper...", request);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("服务注册出错[{}]", request);
            }
        }

        RegisteServiceResponse response =
            RegisteServiceResponse.newBuilder().setSuccess(success).setId(request.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void removeService(RemoveServiceRequest request, StreamObserver<RemoveServiceResponse> responseObserver) {

    }

    public void start() {
        try {
            rpcServer = ServerBuilder.forPort(1000).addService(this).build().start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    MicroServiceImpl.this.stop();
                }
            });
            blockUntilShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void delete(String path) {
        if (!path.startsWith(ROOT_PATH)) {
            return;
        }

        List<String> childNodes = null;
        try {
            childNodes = zooKeeper.getChildren(path, false);
            for (String childNode : childNodes) {
                delete(path + "/" + childNode);
            }
            logger.info("删除[{}]路径节点", path);
            zooKeeper.delete(path, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        logger.info("服务中心rpc关闭...");
        if (rpcServer != null) {
            rpcServer.shutdown();
        }
    }

    public void blockUntilShutdown() throws Exception {
        if (rpcServer != null) {
            logger.info("服务中心rpc启动 ip[{}] 端口[{}]...", NetUtil.getLocalIpAddress(), rpcServer.getPort());
            rpcServer.awaitTermination();
        }
    }
}
