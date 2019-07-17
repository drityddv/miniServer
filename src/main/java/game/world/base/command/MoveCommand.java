package game.world.base.command;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.handler.AbstractMapHandler;
import game.map.handler.IMovableMapHandler;
import game.map.model.Grid;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/4 下午8:36
 */

public class MoveCommand extends AbstractSceneCommand {

    private Player player;
    private Grid targetGrid;
    private AbstractMapHandler handler;

    public MoveCommand(Player player, int currentMapId) {
        super(currentMapId);
        this.player = player;
    }

    public static MoveCommand valueOf(Player player, Grid targetGrid, AbstractMapHandler handler) {
        MoveCommand command = new MoveCommand(player, player.getCurrentMapId());
        command.targetGrid = targetGrid;
        command.handler = handler;
        return command;
    }

    @Override
    public void action() {
        if (handler instanceof IMovableMapHandler) {
            IMovableMapHandler movableMapHandler = (IMovableMapHandler)handler;
            movableMapHandler.move(player, targetGrid);
        }
    }
}
