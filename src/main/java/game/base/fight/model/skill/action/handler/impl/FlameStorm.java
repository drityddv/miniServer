package game.base.fight.model.skill.action.handler.impl;

import java.util.List;

import game.base.fight.base.model.attack.impl.MagicGroupAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.game.attribute.AttributeType;
import game.base.skill.model.BaseSkill;
import utils.MathUtil;

/**
 * 烈焰风暴
 *
 * @author : ddv
 * @since : 2019/7/22 5:33 PM
 */

public class FlameStorm extends BaseActionHandler {

    @Override
    protected void doAction(BaseCreatureUnit caster, List<BaseCreatureUnit> targets, BaseSkill baseSkill) {
        long originValue = baseSkill.getSkillValue();
        originValue +=
            MathUtil.getLongRandom(BattleUtil.getUnitAttributeValue(caster, AttributeType.MAGIC_ATTACK_LOWER),
                BattleUtil.getUnitAttributeValue(caster, AttributeType.MAGIC_ATTACK_UPPER));
        MagicGroupAttack.valueOf(caster, targets, baseSkill, originValue).doActive();
        super.doAction(caster, targets, baseSkill);
    }

}
