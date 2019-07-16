package game.world.fight.syncStrategy;

import game.base.fight.model.pvpunit.FighterAccount;

/**
 * @author : ddv
 * @since : 2019/7/5 下午12:25
 */

public interface ISyncStrategy {

    /**
     * 同步玩家信息
     *
     * @param fighterAccount
     */
    void syncInfo(FighterAccount fighterAccount);
}
