package game.base.executor.command.impl.account.base;

/**
 * @author : ddv
 * @since : 2019/7/2 下午2:18
 */

public abstract class AbstractAccountRateCommand extends AbstractAccountDelayCommand {

    /**
     * 周期
     */
    private long period;

    public AbstractAccountRateCommand(String accountId, long delay, long period) {
        super(accountId, delay);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

}
