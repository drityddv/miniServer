package game.base.fight.model.skill.action.handler.impl;

import java.util.List;

import game.base.fight.base.model.attack.impl.PhysicalSingleAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.game.attribute.AttributeType;
import game.base.skill.model.BaseSkill;
import game.world.fight.model.BattleParam;
import utils.MathUtil;

/**
 * 午时已到 伤害 = 技能初始值+[物理上限~物理下限]的区间随机值 射击后添加物理中毒buff
 *
 * @author : ddv
 * @since : 2019/7/17 10:29 AM
 */

public class HighNoonShoot extends BaseActionHandler {

    @Override
    protected void doAction(BaseCreatureUnit caster, List<BaseCreatureUnit> targets, BaseSkill baseSkill,
        BattleParam battleParam) {
        targets.forEach(target -> {
            long originValue = baseSkill.getSkillValue();
            originValue += MathUtil.getLongRandom(caster.getUnitAttributeValue(AttributeType.PHYSICAL_ATTACK_LOWER),
                caster.getUnitAttributeValue(AttributeType.PHYSICAL_ATTACK_UPPER));
            PhysicalSingleAttack.valueOf(caster, target, baseSkill, originValue, battleParam).doActive();
        });

        triggerBuffs(caster, targets, baseSkill);
    }

}
