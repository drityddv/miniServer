package game.base.executor.account;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import game.base.executor.NameThreadFactory;
import game.base.executor.command.impl.account.base.AbstractAccountCommand;
import game.base.executor.command.impl.account.base.AbstractAccountDelayCommand;
import game.base.executor.command.impl.account.base.AbstractAccountRateCommand;
import game.base.executor.constant.ExecutorConst;
import game.base.executor.util.ExecutorUtils;

/**
 * @author : ddv
 * @since : 2019/6/19 上午12:33
 */
@Component
public class AccountExecutor implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AccountExecutor.class);
    private static final ThreadPoolExecutor[] ACCOUNT_SERVICE = new ThreadPoolExecutor[ExecutorConst.POOL_SIZE];

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (int i = 0; i < ExecutorConst.POOL_SIZE; i++) {
            ThreadPoolExecutor thread =
                new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
                    new NameThreadFactory("accountThread", i), new ThreadPoolExecutor.AbortPolicy());
            ACCOUNT_SERVICE[i] = thread;
        }

        logger.info("初始化账号线程池完毕,场景线程池大小[{}]", ACCOUNT_SERVICE.length);
    }

    public void schedule(AbstractAccountRateCommand accountRateCommand, long delay, long period) {

    }

    /**
     * 延时任务
     *
     * @param command
     * @param delay
     */
    public void schedule(AbstractAccountCommand command, long delay) {
        if (command instanceof AbstractAccountDelayCommand) {
            command.refreshState();
            // command.setFuture();
        }
    }

    public void addTask(AbstractAccountCommand command) {
        final Object key = command.getKey();
        final String taskName = command.getName();
        int modIndex = command.modIndex(ACCOUNT_SERVICE.length);
        try {
            ACCOUNT_SERVICE[modIndex].submit(() -> {
                if (!command.isCanceled()) {
                    command.active();
                }
            });
        } catch (Exception e) {
            logger.error("账号线程池提交任务出错,任务名[{}]", taskName);
            e.printStackTrace();
        }
    }

    public void addTask(int modIndex, String taskName, Runnable task) {
        ACCOUNT_SERVICE[modIndex].submit(task);
    }

    public void shutdown() {
        ExecutorUtils.shutdown(ACCOUNT_SERVICE, "账号");
    }

    public int modIndex(String accountId) {
        return Math.abs(accountId.hashCode() % ACCOUNT_SERVICE.length);
    }
}
