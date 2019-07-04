package game.base.executor.command.impl.account.base;

import game.base.executor.command.AbstractCommand;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:12
 */

public abstract class AbstractAccountCommand extends AbstractCommand {

    private String accountId;

    public AbstractAccountCommand(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public Object getKey() {
        return accountId;
    }

    @Override
    public int modIndex(int poolSize) {
        return Math.abs(accountId.hashCode() % poolSize);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
