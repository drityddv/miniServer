package game.scene.fight.syncStrategy.impl;

import game.base.fight.model.pvpunit.FighterAccount;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.scene.fight.syncStrategy.BasePlayerSyncStrategy;
import game.user.player.model.Player;

/**
 * 等级同步
 *
 * @author : ddv
 * @since : 2019/7/5 下午5:03
 */

public class LevelSynStrategy extends BasePlayerSyncStrategy {

    private int level;

    public static LevelSynStrategy valueOf(Player player) {
        LevelSynStrategy synStrategy = new LevelSynStrategy();
        synStrategy.init(player);
        return synStrategy;
    }

    @Override
    public void init(Player player) {
        super.init(player);
        level = player.getLevel();
    }

    @Override
    public void syncInfo(FighterAccount fighterAccount) {
        PlayerUnit playerUnit = getPlayerUnit(fighterAccount);
        if (playerUnit != null) {
            playerUnit.setLevel(this.level);
        }
    }
}
