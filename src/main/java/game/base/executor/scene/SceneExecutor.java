package game.base.executor.scene;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import game.base.executor.NameThreadFactory;
import game.base.executor.command.AbstractCommand;
import game.base.executor.command.ICommand;
import game.base.executor.constant.ExecutorConst;
import game.base.executor.util.ExecutorUtils;

/**
 * @author : ddv
 * @since : 2019/7/2 上午11:47
 */

@Component
public class SceneExecutor implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SceneExecutor.class);
    private static final ThreadPoolExecutor[] SCENE_SERVICE = new ThreadPoolExecutor[ExecutorConst.POOL_SIZE];

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (int i = 0; i < ExecutorConst.POOL_SIZE; i++) {
            ThreadPoolExecutor thread =
                new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
                    new NameThreadFactory("SceneThread", i), new ThreadPoolExecutor.AbortPolicy());
            SCENE_SERVICE[i] = thread;
        }
        logger.info("初始化场景线程池完毕,场景线程池大小[{}]", SCENE_SERVICE.length);
    }

    public final void addTask(ICommand command) {
        final Object key = command.getKey();
        final String taskName = command.getName();
        int modIndex = command.modIndex(SCENE_SERVICE.length);
        try {
            SCENE_SERVICE[modIndex].submit(() -> {
                if (!command.isCanceled()) {
                    command.active();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("场景线程[{}] 执行任务[{}]出错 key[{}]", modIndex, taskName, key);
        }
    }

    /**
     * 延时命令
     *
     * @param command
     * @param delay
     */
    public final void schedule(AbstractCommand command, long delay) {
        command.refreshState();
        // command.setFuture(SpringContext.getSceneScheduleService());
    }

    public void shutdown() {
        ExecutorUtils.shutdown(SCENE_SERVICE, "场景");
    }
}
