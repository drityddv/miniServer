package game.base.executor.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.command.impl.account.base.AbstractAccountCommand;
import game.base.executor.command.impl.account.base.AbstractAccountDelayCommand;
import game.base.executor.command.impl.account.base.AbstractAccountRateCommand;

/**
 * @author : ddv
 * @since : 2019/7/2 下午2:26
 */
@Component
public class AccountExecutorService implements IAccountExecutorService {

    @Autowired
    private AccountExecutor accountExecutor;

    @Override
    public void addTask(int modIndex, String taskName, Runnable task) {
        accountExecutor.addTask(modIndex, taskName, task);
    }

    @Override
    public void submit(AbstractAccountCommand command) {
        if (command instanceof AbstractAccountRateCommand) {
            AbstractAccountRateCommand accountRateCommand = (AbstractAccountRateCommand)command;
            accountExecutor.schedule(accountRateCommand, accountRateCommand.getDelay(), accountRateCommand.getPeriod());
        } else if (command instanceof AbstractAccountDelayCommand) {
            AbstractAccountDelayCommand accountDelayCommand = (AbstractAccountDelayCommand)command;
            accountExecutor.schedule(accountDelayCommand, accountDelayCommand.getDelay());
        } else {
            accountExecutor.addTask(command);
        }
    }

    @Override
    public void schedule(AbstractAccountCommand command, long delay) {
        accountExecutor.schedule(command, delay);
    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {
        accountExecutor.shutdown();
    }

    @Override
    public int modIndex(String accountId) {
        return accountExecutor.modIndex(accountId);
    }
}
