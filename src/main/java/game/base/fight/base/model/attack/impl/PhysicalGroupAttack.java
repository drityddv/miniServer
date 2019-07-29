package game.base.fight.base.model.attack.impl;

import game.base.fight.base.model.BaseActionEntry;
import game.base.fight.base.model.attack.ActionTypeEnum;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.game.attribute.AttributeType;
import game.base.skill.model.BaseSkill;
import game.world.fight.model.BattleParam;
import utils.MathUtil;

/**
 * 物理伤害型多体指向技能
 *
 * @author : ddv
 * @since : 2019/7/17 10:48 AM
 */

public class PhysicalGroupAttack extends BaseActionEntry {

    public PhysicalGroupAttack(BaseCreatureUnit attacker, BaseCreatureUnit defender, BaseSkill skill, long value,
        BattleParam battleParam) {
        super(attacker, defender, skill, value, ActionTypeEnum.Physical_Attack, battleParam);
    }

    public static PhysicalGroupAttack valueOf(BaseCreatureUnit attacker, BaseCreatureUnit defender, BaseSkill skill,
        long value, BattleParam battleParam) {
        PhysicalGroupAttack attack = new PhysicalGroupAttack(attacker, defender, skill, value, battleParam);
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
