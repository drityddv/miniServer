package game.base.fight.utils;

import java.util.List;
import java.util.Map;

import game.base.fight.base.model.attack.impl.NormalAttack;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.BaseUnit;
import game.base.fight.model.skill.model.PVPSkillComponent;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.skill.model.BaseSkill;
import game.map.handler.AbstractMapHandler;
import game.map.visible.AbstractVisibleMapInfo;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/7/16 3:24 PM
 */

public class BattleUtil {

    // 法力值是否足够
    public static boolean isEnoughMp(BaseUnit unit, long mpConsume) {
        return unit.getCurrentMp() >= mpConsume;
    }

    // 技能是否在cd
    public static boolean skillInCd(BaseSkill baseSkill) {
        return TimeUtil.now() < (baseSkill.getSkillCd() + baseSkill.getLastUsedAt());
    }

    // 获取战斗单元技能
    public static BaseSkill getUnitSkill(BaseUnit unit, long skillId) {
        PVPSkillComponent component = unit.getComponentContainer().getComponent(UnitComponentType.SKILL);
        return component.getSkillMap().get(skillId);
    }

    public static PVPCreatureAttributeComponent getUnitAttrComponent(BaseUnit baseUnit) {
        return baseUnit.getComponentContainer().getComponent(UnitComponentType.ATTRIBUTE);
    }

    // 计算伤害并修改单元数值
    public static void doAttack(BaseCreatureUnit caster, BaseCreatureUnit defender, long skillId) {
        BaseSkill unitSkill = getUnitSkill(caster, skillId);
        long damage = unitSkill.getSkillLevelResource().getValue();
        long damage1 = calculateSkillValue1(damage, unitSkill.getSkillLevelResource().getAttributeTypes(),
            getUnitAttrComponent(caster).getFinalAttributes());
        NormalAttack attack = NormalAttack.valueOf(defender, damage1);
        attack.doAttack(caster, defender, damage1);

    }

    // 计算技能一级数值 [收到属性增益之后的数值]
    public static long calculateSkillValue1(long skillValue, List<AttributeType> attributeTypeList,
        Map<AttributeType, Attribute> attributes) {
        for (AttributeType attributeType : attributeTypeList) {
            Attribute attribute = attributes.get(attributeType);
            if (attribute != null) {
                skillValue += attribute.getValue();
            }
        }
        return skillValue;
    }

    // 找到怪物或者玩家
    public static BaseCreatureUnit findTargetUnit(AbstractMapHandler mapHandler, long targetId, int mapId) {
        BaseCreatureUnit unit = null;
        AbstractVisibleMapInfo mapObject = mapHandler.getPlayerObjects(mapId).get(targetId);
        if (mapObject == null) {
            mapObject = mapHandler.getMonsterObjects(mapId).get(targetId);
        }
        if (mapObject == null) {
            return unit;
        }
        unit = mapObject.getFighterAccount().getCreatureUnit();
        return unit;
    }
}
