import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.ebus.EventBus;
import game.base.executor.account.AccountExecutor;
import game.scene.npc.service.NpcManager;
import game.user.pack.service.IPackService;
import game.world.neutral.neutralMap.service.INeutralMapService;
import middleware.resource.StorageManager;
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

        // 初始化派发器和eventBus
        SpringContext.getDispatcher().init();

        // 初始化静态资源组件
        SpringContext.getStorageManager().init();

        // 中立地图初始化
        SpringContext.getNeutralMapService().init();

        // net服务器启动
        new Server().run();

        StorageManager storageManager = SpringController.getContext().getBean(StorageManager.class);

        AccountExecutor accountExecutor = SpringController.getContext().getBean(AccountExecutor.class);

        EventBus eventBus = SpringController.getContext().getBean(EventBus.class);

        IPackService packService = SpringContext.getPackService();

        INeutralMapService neutralMapService = SpringContext.getNeutralMapService();

        NpcManager npcManager = SpringContext.getNpcManager();

        logger.info("服务器启动成功...");
    }
}
