package micro.zookeeper.micro.service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 自身也是一个rpc服务 提供服务治理接口
 *
 * @author : ddv
 * @since : 2019/12/10 5:41 PM
 */

public class ZookeeperCenter {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperCenter.class);
    public MicroServiceImpl microService;
    public ZooKeeper zooKeeper;
    public CountDownLatch countDownLatch = new CountDownLatch(1);

    public void initConn() {
        logger.info("初始化zookeeper服务....");
        try {
            zooKeeper = new ZooKeeper("172.19.0.5:2181", Integer.MAX_VALUE, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        logger.info("[回调] zookeeper连接成功!");
                        init();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ZookeeperCenter center = new ZookeeperCenter();
        center.initConn();
        try {
            center.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        if (microService == null) {
            microService = MicroServiceImpl.valueOf(this);
            microService.init();
            microService.start();
            logger.info("micro.zookeeper conn asyn call back trigger rpc ...");
        }
    }

}
