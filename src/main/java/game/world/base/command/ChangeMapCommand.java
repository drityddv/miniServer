package game.world.base.command;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.user.player.model.Player;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/2 下午8:25
 */

public class ChangeMapCommand extends AbstractSceneCommand {

    private Player player;

    private boolean clientRequest;

    private int targetMapId;

    public ChangeMapCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    public static ChangeMapCommand valueOf(Player player, int mapId, boolean clientRequest) {
        ChangeMapCommand command = new ChangeMapCommand(player.getAccountId(), player.getCurrentMapId());
        command.player = player;
        command.targetMapId = mapId;
        command.clientRequest = clientRequest;
        return command;
    }

    @Override
    public void action() {
        SpringContext.getWorldService().gatewayChangeMap(player, targetMapId, clientRequest);
    }

    @Override
    public String getName() {
        return "ChangeMapCommand";
    }
}
