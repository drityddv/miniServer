package game.base.fight.base.model.attack.impl;

import game.base.fight.base.model.BaseActionEntry;
import game.base.fight.base.model.attack.ActionTypeEnum;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.game.attribute.AttributeType;
import game.base.skill.model.BaseSkill;
import game.world.fight.model.BattleParam;
import utils.MathUtil;

/**
 * @author : ddv
 * @since : 2019/7/22 5:42 PM
 */

public class MagicGroupAttack extends BaseActionEntry {

    public MagicGroupAttack(BaseCreatureUnit caster, BaseCreatureUnit defender, BaseSkill baseSkill, long value,
        BattleParam battleParam) {
        super(caster, defender, baseSkill, value, ActionTypeEnum.Magic_Attack, battleParam);
    }

    public static MagicGroupAttack valueOf(BaseCreatureUnit caster, BaseCreatureUnit defender, BaseSkill baseSkill,
        long originValue, BattleParam battleParam) {
        MagicGroupAttack attack = new MagicGroupAttack(caster, defender, baseSkill, originValue, battleParam);
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
