package micro.match.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import micro.match.MatchServiceGrpc;
import micro.match.service.MatchServiceImpl;

/**
 * @author : ddv
 * @since : 2019/12/10 4:37 PM
 */

public class MatchServer {
    private static Logger logger = LoggerFactory.getLogger(MatchServer.class);
    private static Map<String, BindableService> serverMap = new ConcurrentHashMap<>();

    private int port = 1000;
    private MatchServiceGrpc.MatchServiceImplBase service;
    private Server rpcServer;

    private ZooKeeper zooKeeper;

    public static void main(String[] args) {
        MatchServer server = new MatchServer();
        try {
            server = new MatchServer();
            server.init();
            server.start();
        } catch (Exception e) {
            logger.error("匹配服务出现致命错误,关闭节点!");
        } finally {
            server.stop();
            // call micro.zookeeper remove this pod
        }

    }

    private void init() {
        service = new MatchServiceImpl();
		connZookeeper();
    }

	private void connZookeeper() {

	}

	private void start() throws Exception {
        rpcServer = ServerBuilder.forPort(port).addService(service).build().start();
        logger.info("匹配服务启动, 监听端口[{}]", port);
        // register server top micro.zookeeper
        blockUntilShutdown();
    }

    private void stop() {
        if (rpcServer != null) {
            rpcServer.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (rpcServer != null) {
            rpcServer.awaitTermination();
        }
    }
}
