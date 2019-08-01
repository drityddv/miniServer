package game.map.visible.impl;

import game.base.fight.model.pvpunit.FighterAccount;
import game.map.visible.BaseAttackAbleMapObject;
import game.world.base.resource.CreatureResource;
import utils.snow.IdUtil;

/**
 * @author : ddv
 * @since : 2019/7/11 5:08 PM
 */

public class MonsterMapObject extends BaseAttackAbleMapObject {

    private long monsterId;

    // 怪物名称
    private String monsterName;

    public static MonsterMapObject valueOf(CreatureResource creatureResource, int mapId, long sceneId) {
        MonsterMapObject monster = new MonsterMapObject();
        monster.monsterId = IdUtil.getLongId();
        monster.monsterName = creatureResource.getObjectName();
        monster.init(creatureResource.getBornX(), creatureResource.getBornY());
        monster.fighterAccount =
            FighterAccount.valueOfMonster(creatureResource, monster.monsterId, mapId, sceneId, monster);
        return monster;
    }

    @Override
    public long getId() {
        return monsterId;
    }

    @Override
    public String getAccountId() {
        return monsterName;
    }

    public long getMonsterId() {
        return monsterId;
    }

    public String getMonsterName() {
        return monsterName;
    }
}
