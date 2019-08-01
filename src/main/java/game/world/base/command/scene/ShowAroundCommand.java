package game.world.base.command.scene;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.handler.AbstractMapHandler;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/30 4:45 PM
 */

public class ShowAroundCommand extends AbstractSceneCommand {
    private Player player;

    public ShowAroundCommand(Player player) {
        super(player.getCurrentMapId(), player.getCurrentSceneId());
        this.player = player;
    }

    public static ShowAroundCommand valueOf(Player player) {

        return new ShowAroundCommand(player);
    }

    @Override
    public void action() {
        AbstractMapHandler handler = AbstractMapHandler.getAbstractMapHandler(mapId);
        handler.showAround(player);
    }
}
