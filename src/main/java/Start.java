import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        // 初始化定时组件
        SpringContext.getQuartzService().init();

        // 主城地图初始化
        SpringContext.getMainCityService().init();

        // 中立地图初始化
        SpringContext.getNeutralMapService().init();

        // 初始化公共服务
        SpringContext.getSystemService().init();

        // net服务器启动
        SpringContext.getServer().run();

        logger.info("服务器启动成功...");
    }
}
