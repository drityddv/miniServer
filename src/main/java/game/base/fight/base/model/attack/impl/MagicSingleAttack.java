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

public class MagicSingleAttack extends BaseActionEntry {

    public MagicSingleAttack(BaseCreatureUnit attacker, BaseCreatureUnit defender, BaseSkill skill, long value,
        BattleParam battleParam) {
        super(attacker, defender, skill, value, ActionTypeEnum.Magic_Attack, battleParam);
    }

    public static MagicSingleAttack valueOf(BaseCreatureUnit attacker, BaseCreatureUnit defender, BaseSkill skill,
        long value, BattleParam battleParam) {
        MagicSingleAttack attack = new MagicSingleAttack(attacker, defender, skill, value, battleParam);
        return attack;
    }

    @Override
    public void calculate() {
        long originValue = baseSkill.getSkillValue();
        originValue += MathUtil.getLongRandom(caster.getUnitAttributeValue(AttributeType.MAGIC_ATTACK_LOWER),
            caster.getUnitAttributeValue(AttributeType.MAGIC_ATTACK_UPPER));
        long magicArmor = defender.getUnitAttributeValue(AttributeType.MAGIC_ARMOR);
        value = originValue > magicArmor ? originValue - magicArmor : 0;
    }
}
