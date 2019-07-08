package game.base.executor.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/2 下午2:33
 */

public class ExecutorUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorUtils.class);

    public static void shutdown(ThreadPoolExecutor[] executors, String serviceName) {
        for (ThreadPoolExecutor executor : executors) {
            if (executor != null) {
                executor.shutdown();
            }
        }
        for (ThreadPoolExecutor executor : executors) {
            try {
                while (!executor.awaitTermination(10L, TimeUnit.SECONDS)) {
                    BlockingQueue<Runnable> queue = executor.getQueue();
                    logger.info("[{}]线程池延迟关闭,剩余任务[{}]", serviceName, queue.size());
                }
            } catch (Exception e) {
                logger.warn("[{}]线程池延迟关闭捕捉错误,[{}]", serviceName, e.getMessage());
            }
        }
    }

    /**
     * 提交命令 这里暂时全部走地图线程
     *
     * @param command
     */
    public static void submit(AbstractSceneCommand command) {
        SpringContext.getSceneExecutorService().submit(command);
    }

}
