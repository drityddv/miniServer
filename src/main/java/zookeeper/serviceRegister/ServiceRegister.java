package zookeeper.serviceRegister;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import zookeeper.service.IServiceRegister;

/**
 * @author : ddv
 * @since : 2019/12/10 10:11 AM
 */

@Component
public class ServiceRegister implements IServiceRegister {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRegister.class);

    private ZooKeeper zooKeeper;

    @Override
    public void init() {
        logger.info("初始化zookeeper....");
        try {
            zooKeeper = new ZooKeeper("172.19.0.5:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        logger.info("zookeeper 连接成功!");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
