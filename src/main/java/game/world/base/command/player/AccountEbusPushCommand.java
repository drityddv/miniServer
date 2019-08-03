package game.world.base.command.player;

import ebus.model.IEvent;
import game.base.executor.command.impl.account.base.AbstractAccountCommand;
import game.role.player.model.Player;
import spring.SpringContext;

/**
 * 账号线程推送事件命令
 *
 * @author : ddv
 * @since : 2019/8/2 8:31 PM
 */

public class AccountEbusPushCommand extends AbstractAccountCommand {

    private IEvent event;

    public AccountEbusPushCommand(String accountId) {
        super(accountId);
    }

    public static AccountEbusPushCommand valueOf(Player player, IEvent event) {
        AccountEbusPushCommand command = new AccountEbusPushCommand(player.getAccountId());
        command.event = event;
        return command;
    }

    @Override
    public void active() {
        SpringContext.getEventBus().pushEventSyn(event);
    }

}
