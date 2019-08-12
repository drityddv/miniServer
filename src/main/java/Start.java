import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.system.model.mbean.MiniMBean;
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
        try {
            // 初始化spring
            SpringController.init();

            // 初始化派发器和eventBus
            SpringContext.getDispatcher().init();

            // 初始化静态资源组件
            SpringContext.getStorageManager().init();

            // 主城地图初始化
            SpringContext.getMainCityService().init();

            // 中立地图初始化
            SpringContext.getNeutralMapService().init();

            // 单人副本地图初始化
            SpringContext.getSingleInstanceService().init();

            // 多人副本地图初始化
            SpringContext.getGroupInstanceService().init();

            // 初始化定时组件
            SpringContext.getQuartzService().init();

            // 初始化公共服务
            SpringContext.getSystemService().init();

            // 初始化redis
            SpringContext.getRedisService().init();

            // 初始化排行榜
            SpringContext.getRankService().init();

            // net服务器启动
            SpringContext.getServer().run();

            initMBean();
        } catch (Exception e) {
            logger.info("服务器启动失败...");
            e.printStackTrace();
            SpringContext.getSystemService().serverClose();
            return;
        }

        logger.info("服务器启动成功...");
    }

    private static void initMBean() {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            MiniMBean runtime = new MiniMBean();
            ObjectName name = new ObjectName("game.system.model.mbean:type=RuntimeMbean");
            mBeanServer.registerMBean(runtime, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
