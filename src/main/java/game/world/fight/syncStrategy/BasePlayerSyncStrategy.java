package game.world.fight.syncStrategy;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.FighterAccount;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/5 下午12:27
 */

public abstract class BasePlayerSyncStrategy implements ISyncStrategy {

    private String accountId;

    public void init(Player player) {
        this.accountId = player.getAccountId();
    }

    // 地图中业务同步 战斗都用这个playerUnit
    public PlayerUnit getPlayerUnit(FighterAccount fighterAccount) {
        BaseCreatureUnit creatureUnit = fighterAccount.getCreatureUnit();
        if (creatureUnit instanceof PlayerUnit) {
            return (PlayerUnit)creatureUnit;
        }
        return null;
    }

}
