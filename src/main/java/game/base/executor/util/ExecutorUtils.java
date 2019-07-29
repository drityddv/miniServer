package game.base.executor.util;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.command.impl.account.base.AbstractAccountCommand;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/2 下午2:33
 */

public class ExecutorUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorUtils.class);

    public static void shutdown(ThreadPoolExecutor[] executors, String serviceName) {
        logger.info("正在关闭[{}]线程池", serviceName);
        for (ThreadPoolExecutor executor : executors) {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    /**
     * 提交命令
     *
     * @param command
     */
    public static void submit(AbstractSceneCommand command) {
        SpringContext.getSceneExecutorService().submit(command);
    }

    public static void submit(AbstractAccountCommand command) {
        SpringContext.getAccountExecutorService().submit(command);
    }

}
