package game.base.fight.model.pvpunit;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.id.AttributeIdEnum;
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
        UnitComponentContainer.registerComponentClazz(MonsterUnit.class, map);
    }

    // 怪物配置表
    private CreatureResource creatureResource;

    public MonsterUnit(CreatureResource creatureResource, FighterAccount fighterAccount, long id) {
        super(id, fighterAccount, creatureResource.getObjectName());
    }

    public static MonsterUnit valueOf(CreatureResource creatureResource, FighterAccount fighterAccount, long id) {
        MonsterUnit monsterUnit = new MonsterUnit(creatureResource, fighterAccount, id);
        monsterUnit.creatureResource = creatureResource;
        monsterUnit.fighterAccount = fighterAccount;
        monsterUnit.level = creatureResource.getLevel();

        monsterUnit.initComponent();
        PVPCreatureAttributeComponent unitAttributeComponent = monsterUnit.getAttributeComponent();
        unitAttributeComponent.putAttributes(AttributeIdEnum.BASE, creatureResource.getAttributeList());
        unitAttributeComponent.setOwner(monsterUnit);
        unitAttributeComponent.containerRecompute();

        monsterUnit.currentHp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_HP).getValue();
        monsterUnit.currentMp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_MP).getValue();
        monsterUnit.mapId = creatureResource.getMapId();
        return monsterUnit;
    }

    private PVPCreatureAttributeComponent getAttributeComponent() {
        return componentContainer.getComponent(UnitComponentType.ATTRIBUTE);
    }

    public CreatureResource getCreatureResource() {
        return creatureResource;
    }
}
