package game.base.executor.command.impl.scene.impl.rate;

import game.base.executor.command.impl.scene.base.AbstractSceneRateCommand;
import game.map.handler.AbstractMapHandler;

/**
 * @author : ddv
 * @since : 2019/7/15 9:29 AM
 */

public class SceneHeartBeatCommand extends AbstractSceneRateCommand {

    public SceneHeartBeatCommand(String accountId, int mapId, long delay, long period) {
        super(accountId, mapId, delay, period);
    }

    public static SceneHeartBeatCommand valueOf(int mapId, long delay, long period) {
        SceneHeartBeatCommand command = new SceneHeartBeatCommand(null, mapId, delay, period);
        return command;
    }

    @Override
    public void action() {
        AbstractMapHandler handler = AbstractMapHandler.getAbstractMapHandler(mapId);
        handler.heartBeat(mapId);
    }
}
