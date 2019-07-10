package game.base.fight.model.pvpunit;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;
import game.world.base.resource.CreatureResource;

/**
 * 怪物单元
 *
 * @author : ddv
 * @since : 2019/7/8 上午9:23
 */

public class MonsterUnit extends BaseCreatureUnit {
    private static Logger logger = LoggerFactory.getLogger(MonsterUnit.class);

    static {
        Map<UnitComponentType, Class<? extends IUnitComponent>> map = new HashMap<>();
        map.put(UnitComponentType.ATTRIBUTE, PVPCreatureAttributeComponent.class);
        UnitComponentContainer.registerComponentClazz(PlayerUnit.class, map);
    }

    // 怪物配置表
    private CreatureResource creatureResource;

    public static MonsterUnit valueOf(CreatureResource creatureResource, FighterAccount fighterAccount) {
        MonsterUnit monsterUnit = new MonsterUnit();
        monsterUnit.creatureResource = creatureResource;
        monsterUnit.fighterAccount = fighterAccount;
        monsterUnit.setLevel(creatureResource.getLevel());
        monsterUnit.initComponent();
        return monsterUnit;
    }
}
