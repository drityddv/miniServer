package game.base.fight.model.skill.action.handler.impl;

import game.base.fight.base.model.attack.impl.PhysicalGroupAttack;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.game.attribute.AttributeType;

/**
 * 午时已到 伤害 = 技能初始值+[物理上限~物理下限]的区间随机值 射击后添加物理中毒buff
 *
 * @author : ddv
 * @since : 2019/7/17 10:29 AM
 */

public class HighNoonShoot extends BaseActionHandler {

    @Override
    protected void doAction() {
        super.doAction();
        long originValue = getSkillValue();
        originValue +=
            BattleUtil.getLongRandom(BattleUtil.getUnitAttributeValue(caster, AttributeType.PHYSICAL_ATTACK_LOWER),
                BattleUtil.getUnitAttributeValue(caster, AttributeType.PHYSICAL_ATTACK_UPPER));
        PhysicalGroupAttack attack = PhysicalGroupAttack.valueOf(caster, defenders, baseSkill, originValue);
        attack.doActive();
    }

}
