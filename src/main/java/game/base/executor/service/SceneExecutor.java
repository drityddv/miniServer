package game.base.executor.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import game.base.executor.NameThreadFactory;
import game.base.executor.constant.ExecutorConst;

/**
 * @author : ddv
 * @since : 2019/6/19 上午12:46
 */
@Component
public class SceneExecutor implements ApplicationListener<ContextRefreshedEvent> {

    private ThreadPoolExecutor[] mapPool = new ThreadPoolExecutor[ExecutorConst.POOL_SIZE];

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (int i = 0; i < ExecutorConst.POOL_SIZE; i++) {
            ThreadPoolExecutor thread =
                new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
                    new NameThreadFactory("mapThread", i), new ThreadPoolExecutor.AbortPolicy());
            mapPool[i] = thread;
        }
    }

    public ThreadPoolExecutor getThread(long mapId) {
        return mapPool[Math.abs((int)mapId % mapPool.length)];
    }

}
