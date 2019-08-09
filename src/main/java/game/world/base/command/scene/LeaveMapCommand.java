package game.world.base.command.scene;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.executor.util.ExecutorUtils;
import game.map.handler.AbstractMapHandler;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;

/**
 * @author : ddv
 * @since : 2019/7/4 下午9:36
 */

public class LeaveMapCommand extends AbstractSceneCommand {

    private Player player;

    private int newMapId;
    private long newSceneId;

    public LeaveMapCommand(Player player) {
        super(player.getCurrentMapId(), player.getCurrentSceneId());
    }

    public static LeaveMapCommand valueOf(Player player, int newMapId, long newSceneId) {
        LeaveMapCommand command = new LeaveMapCommand(player);
        command.player = player;
        command.newMapId = newMapId;
        command.newSceneId = newSceneId;
        return command;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void action() {
        try {
            if (player.isChangingMap()) {
                return;
            }
            // 具体切图逻辑
            MiniMapResource mapResource = WorldManager.getInstance().getMapResource(mapId);
            AbstractMapHandler handler = AbstractMapHandler.getHandler(mapResource.getGroupId());

            handler.leaveMapPre(player);
            handler.leaveMap(player);
            handler.leaveMapAfter(player);
            if (newMapId != 0) {
                ExecutorUtils.submit(EnterMapCommand.valueOf(player, newMapId, newSceneId));
            }
        } catch (Exception e) {
            player.setChangingMap(false);
            e.printStackTrace();
        }
    }
}
