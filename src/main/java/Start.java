import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.scene.map.service.SceneMapManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import middleware.resource.storage.StorageManager;
import net.server.Server;
import spring.SpringController;

/**
 * @author : ddv
 * @since : 2019/4/29 下午3:16
 */

public class Start {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        // 初始化spring
        SpringController.init();

        // net服务器启动
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        singleThreadExecutor.submit(() -> {
            Server server = new Server();
            server.init();
            server.run();
        });

		StorageManager storageManager = SpringController.getContext().getBean(StorageManager.class);

		SceneMapManager mapManager = SpringController.getContext().getBean(SceneMapManager.class);

		logger.info("服务器启动成功,Start线程关闭...");
    }
}
