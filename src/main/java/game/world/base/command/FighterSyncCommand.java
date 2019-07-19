package game.world.base.command;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.FighterAccount;
import game.map.base.AbstractMovableScene;
import game.map.base.AbstractScene;
import game.map.handler.AbstractMapHandler;
import game.world.fight.syncStrategy.ISyncStrategy;

/**
 * @author : ddv
 * @since : 2019/7/5 下午11:56
 */

public class FighterSyncCommand extends AbstractSceneCommand {

    private long playerId;

    private ISyncStrategy iSyncStrategy;

    public FighterSyncCommand(int mapId, long playerId, ISyncStrategy iSyncStrategy) {
        super(mapId);
        this.playerId = playerId;
        this.iSyncStrategy = iSyncStrategy;
    }

    public static FighterSyncCommand valueOf(long playerId, ISyncStrategy syncStrategy, int mapId) {
        FighterSyncCommand command = new FighterSyncCommand(mapId, playerId, syncStrategy);
        return command;
    }

    @Override
    public void action() {
        AbstractScene mapScene = AbstractMapHandler.getHandler(mapId).getMapScene(mapId);
//		mapScene.getPlayerMap()
//        FighterAccount fighterAccount = ((AbstractMovableScene) mapScene).getPlayerMap().get(playerId);
//        if (fighterAccount != null) {
//            iSyncStrategy.syncInfo(fighterAccount);
//        }
    }

    @Override
    public String getName() {
        return "FighterSyncCommand";
    }
}
