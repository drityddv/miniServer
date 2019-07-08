package game.world.base.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.miniMap.handler.AbstractMapHandler;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;

/**
 * @author : ddv
 * @since : 2019/7/4 下午3:25
 */

public class LogMapCommand extends AbstractSceneCommand {

    private static final Logger logger = LoggerFactory.getLogger(LogMapCommand.class);

    private Player player;

    public LogMapCommand(Player player, int mapId) {
        super(player.getAccountId(), mapId);
    }

    public static LogMapCommand valueOf(Player player, int mapId) {
        LogMapCommand command = new LogMapCommand(player, mapId);
        command.player = player;
        return command;
    }

    @Override
    public void action() {
        MiniMapResource mapResource = WorldManager.getInstance().getMapResource(mapId);
        if (mapResource == null) {
            logger.warn("打印地图[{}]出错,服务器没有这张地图", mapId);
            return;
        }
        AbstractMapHandler handler = AbstractMapHandler.getHandler(mapResource.getGroupId());
        handler.logMap(player, mapId);
    }

}
