package game.world.base.command;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.FighterAccount;
import game.map.base.AbstractScene;
import game.world.fight.syncStrategy.ISyncStrategy;

/**
 * @author : ddv
 * @since : 2019/7/5 下午11:56
 */

public class FighterSyncCommand extends AbstractSceneCommand {

    private long playerId;

    private ISyncStrategy iSyncStrategy;

    public FighterSyncCommand(String accountId, AbstractScene scene) {
        super(scene, accountId);
    }

    public static FighterSyncCommand valueOf(long playerId, String accountId, ISyncStrategy syncStrategy,
        AbstractScene scene) {
        FighterSyncCommand command = new FighterSyncCommand(accountId, scene);
        command.playerId = playerId;
        command.iSyncStrategy = syncStrategy;
        return command;
    }

    @Override
    public void action() {
        FighterAccount fighterAccount = scene.getPlayerFighter(playerId).getFighterAccount();
        if (fighterAccount != null) {
            iSyncStrategy.syncInfo(fighterAccount);
        }
    }

    @Override
    public String getName() {
        return "FighterSyncCommand";
    }
}
