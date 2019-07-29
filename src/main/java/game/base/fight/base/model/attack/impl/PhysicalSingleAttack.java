package game.base.fight.base.model.attack.impl;

import game.base.fight.base.model.BaseActionEntry;
import game.base.fight.base.model.attack.ActionTypeEnum;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.game.attribute.AttributeType;
import game.base.skill.model.BaseSkill;
import game.world.fight.model.BattleParam;
import utils.MathUtil;

/**
 * 魔法伤害性单体攻击技能
 *
 * @author : ddv
 * @since : 2019/7/16 5:03 PM
 */

public class PhysicalSingleAttack extends BaseActionEntry {

    public PhysicalSingleAttack(BaseCreatureUnit attacker, BaseCreatureUnit defender, BaseSkill skill, long value,
        BattleParam battleParam) {
        super(attacker, defender, skill, value, ActionTypeEnum.Physical_Attack, battleParam);
    }

    public static PhysicalSingleAttack valueOf(BaseCreatureUnit attacker, BaseCreatureUnit defender, BaseSkill skill,
        long value, BattleParam battleParam) {
        PhysicalSingleAttack attack = new PhysicalSingleAttack(attacker, defender, skill, value, battleParam);
        return attack;
    }

    @Override
    public void calculate() {
        long originValue = baseSkill.getSkillValue();
        originValue += MathUtil.getLongRandom(caster.getUnitAttributeValue(AttributeType.PHYSICAL_ATTACK_LOWER),
            caster.getUnitAttributeValue(AttributeType.PHYSICAL_ATTACK_UPPER));
        long physicalArmor = defender.getUnitAttributeValue(AttributeType.PHYSICAL_ARMOR);
        value = originValue > physicalArmor ? originValue - physicalArmor : 0;
    }
}
