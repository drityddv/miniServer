package game.base.executor.account;

import game.base.executor.command.impl.account.base.AbstractAccountCommand;

/**
 * @author : ddv
 * @since : 2019/7/2 下午2:21
 */

public interface IAccountExecutorService {

    /**
     * 直接执行
     *
     * @param modIndex
     * @param taskName
     * @param task
     */
    void addTask(int modIndex, final String taskName, final Runnable task);

    /**
     * 增加命令
     *
     * @param command
     */
    void submit(AbstractAccountCommand command);

    /**
     * 延时执行
     *
     * @param command
     * @param delay
     */
    void schedule(AbstractAccountCommand command, long delay);

    /**
     * 初始化
     */
    void init();

    /**
     * 关闭支持
     */
    void shutdown();

    /**
     * modIndex
     *
     * @param accountId
     * @return
     */
    int modIndex(String accountId);
}
