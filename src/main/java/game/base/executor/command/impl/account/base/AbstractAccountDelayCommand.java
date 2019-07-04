package game.base.executor.command.impl.account.base;

/**
 * @author : ddv
 * @since : 2019/7/2 下午2:17
 */

public abstract class AbstractAccountDelayCommand extends AbstractAccountCommand {
    /**
     * 延时
     */
    private long delay;

    public AbstractAccountDelayCommand(String accountId, long delay) {
        super(accountId);
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
