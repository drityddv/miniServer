package game.world.base.command.player;

import java.util.List;

import game.base.executor.command.impl.account.base.AbstractAccountCommand;
import game.base.item.base.model.AbstractItem;
import game.role.player.model.Player;
import spring.SpringContext;

/**
 * 添加道具到背包
 *
 * @author : ddv
 * @since : 2019/7/25 11:28 PM
 */

public class AddItemToPackCommand extends AbstractAccountCommand {
    private Player player;
    private List<AbstractItem> itemList;

    public AddItemToPackCommand(Player player, List<AbstractItem> itemList) {
        super(player.getAccountId());
        this.player = player;
        this.itemList = itemList;
    }

    public static AddItemToPackCommand valueOf(Player player, List<AbstractItem> itemList) {
        AddItemToPackCommand command = new AddItemToPackCommand(player, itemList);
        return command;
    }

    @Override
    public void active() {
        if (SpringContext.getPackService().isEnoughSize(player, itemList)) {
            SpringContext.getPackService().addItems(player, itemList);
        }
    }
}
