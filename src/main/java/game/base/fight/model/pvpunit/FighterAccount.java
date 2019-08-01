package game.base.fight.model.pvpunit;

import game.map.visible.BaseAttackAbleMapObject;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.role.player.model.Player;
import game.world.base.resource.CreatureResource;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:28
 */

public class FighterAccount {

    private String accountId;

    // 战斗单位
    private BaseCreatureUnit creatureUnit;

    private BaseAttackAbleMapObject mapObject;

    public static FighterAccount valueOfPlayer(Player player, int mapId, long sceneId, PlayerMapObject mapObject) {
        FighterAccount fighterAccount = new FighterAccount();
        fighterAccount.setAccountId(player.getAccountId());
        fighterAccount.mapObject = mapObject;
        fighterAccount.setCreatureUnit(PlayerUnit.valueOf(player, fighterAccount, mapId, sceneId));
        return fighterAccount;
    }

    public static FighterAccount valueOfMonster(CreatureResource creatureResource, long id, int mapId, long sceneId,
        MonsterMapObject mapObject) {
        FighterAccount fighterAccount = new FighterAccount();
        fighterAccount.mapObject = mapObject;
        fighterAccount.setAccountId(creatureResource.getObjectName());
        fighterAccount.setCreatureUnit(MonsterUnit.valueOf(creatureResource, fighterAccount, id, mapId, sceneId));
        return fighterAccount;
    }

    // get and set
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BaseAttackAbleMapObject getMapObject() {
        return mapObject;
    }

    public BaseCreatureUnit getCreatureUnit() {
        return creatureUnit;
    }

    public void setCreatureUnit(BaseCreatureUnit creatureUnit) {
        this.creatureUnit = creatureUnit;
    }
}
