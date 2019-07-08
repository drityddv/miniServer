package game.user.login.command;

import game.base.executor.command.impl.account.base.AbstractAccountCommand;

/**
 * netty抛出的用户登出命令
 * @author : ddv
 * @since : 2019/7/8 下午3:31
 */

public class UserLogoutCommand extends AbstractAccountCommand {

    public static UserLogoutCommand valueOf(String accountId) {
        UserLogoutCommand command = new UserLogoutCommand(accountId);
        return command;
    }

    public UserLogoutCommand(String accountId) {
        super(accountId);
    }

    @Override
    public void active() {

    }
}
