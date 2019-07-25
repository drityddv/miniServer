package game.base.executor.account;

import java.lang.reflect.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.command.impl.account.base.AbstractAccountCommand;
import game.dispatch.HandlerInvoke;
import net.model.USession;
import utils.SimpleUtil;

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
    public void handle(HandlerInvoke handlerInvoke, Object param, int modIndex, Object packet) {
        addTask(modIndex, packet.getClass().getSimpleName(), () -> {
            handlerInvoke.invoke(param, packet);
        });
    }

    @Override
    public Object getParam(HandlerInvoke handlerInvoke, USession session) {
        Object param = session;
        Parameter parameter = handlerInvoke.getMethod().getParameters()[0];
        Class<?> type = parameter.getType();
        Class<? extends USession> aClass = session.getClass();
        if ((type != aClass)) {
            param = SimpleUtil.getPlayerFromSession(session);
        }
        return param;
    }

    @Override
    public void submit(AbstractAccountCommand command) {
        accountExecutor.addTask(command);
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
