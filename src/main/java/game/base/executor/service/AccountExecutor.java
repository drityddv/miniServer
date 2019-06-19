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
 * @since : 2019/6/19 上午12:33
 */
@Component
public class AccountExecutor implements ApplicationListener<ContextRefreshedEvent> {

    private ThreadPoolExecutor[] accountPool = new ThreadPoolExecutor[ExecutorConst.POOL_SIZE];

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (int i = 0; i < ExecutorConst.POOL_SIZE; i++) {
            ThreadPoolExecutor thread =
                new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
                    new NameThreadFactory("accountThread", i), new ThreadPoolExecutor.AbortPolicy());
            accountPool[i] = thread;
        }
    }

    public ThreadPoolExecutor getThread(String accountId) {
        return accountPool[Math.abs(accountId.hashCode() % accountPool.length)];
    }

}
