package game.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/10 下午8:46
 */
@Component
public class SystemService implements ISystemService {

    private static Logger logger = LoggerFactory.getLogger(SystemService.class);

    @Override
    public void serverClose() {
        logger.info("服务器开始关服...");
        SpringContext.getServer().shutdown();
        SpringContext.getQuartzService().shutdown();
        SpringContext.getSceneExecutorService().shutdown();
        SpringContext.getAccountExecutorService().shutdown();
        logger.info("服务器关闭结束...");
    }

    @Override
    public void init() {
        initShutdownHook();

    }

    private void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> SpringContext.getSystemService().serverClose()));
    }
}
