import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.ebus.EventBus;
import game.base.executor.account.AccountExecutor;
import game.base.executor.service.MiniExecutorService;
import game.user.pack.service.IPackService;
import game.world.neutralMap.service.INeutralMapService;
import middleware.resource.storage.StorageManager;
import net.server.Server;
import spring.SpringContext;
import spring.SpringController;

/**
 * @author : ddv
 * @since : 2019/4/29 下午3:16
 */

public class Start {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // 初始化spring
        SpringController.init();

        // 中立地图初始化
        SpringContext.getNeutralMapService().init();

        // net服务器启动
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        singleThreadExecutor.submit(() -> {
            Server server = new Server();
            server.init();
            server.run();
        });

        StorageManager storageManager = SpringController.getContext().getBean(StorageManager.class);

        AccountExecutor accountExecutor = SpringController.getContext().getBean(AccountExecutor.class);

        MiniExecutorService miniExecutorService = SpringController.getContext().getBean(MiniExecutorService.class);

        EventBus eventBus = SpringController.getContext().getBean(EventBus.class);

        IPackService packService = SpringContext.getPackService();

        INeutralMapService neutralMapService = SpringContext.getNeutralMapService();

        logger.info("服务器启动成功...");
    }
}
