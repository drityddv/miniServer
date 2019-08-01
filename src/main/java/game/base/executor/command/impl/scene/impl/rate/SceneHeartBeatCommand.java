package game.base.executor.command.impl.scene.impl.rate;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.handler.AbstractMapHandler;

/**
 * @author : ddv
 * @since : 2019/7/15 9:29 AM
 */

public class SceneHeartBeatCommand extends AbstractSceneCommand {

    public SceneHeartBeatCommand(int mapId) {
        super(mapId, 0);
    }

    public static SceneHeartBeatCommand valueOf(int mapId) {
        SceneHeartBeatCommand command = new SceneHeartBeatCommand(mapId);
        return command;
    }

    @Override
    public void action() {
        AbstractMapHandler handler = AbstractMapHandler.getAbstractMapHandler(mapId);
        logger.info("地图[{}] 心跳任务", mapId);
        handler.heartBeat(mapId);
    }
}
