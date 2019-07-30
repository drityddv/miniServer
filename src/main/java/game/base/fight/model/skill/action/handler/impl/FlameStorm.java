package game.base.fight.model.skill.action.handler.impl;

import java.util.List;

import game.base.fight.base.model.attack.impl.MagicGroupAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.game.attribute.AttributeType;
import game.base.skill.model.BaseSkill;
import game.world.fight.model.BattleParam;
import utils.MathUtil;

/**
 * 烈焰风暴
 *
 * @author : ddv
 * @since : 2019/7/22 5:33 PM
 */

public class FlameStorm extends BaseActionHandler {

    @Override
    protected void doAction(BaseCreatureUnit caster, List<BaseCreatureUnit> targets, BaseSkill baseSkill,
        BattleParam battleParam) {
        targets.forEach(target -> {
            long originValue = baseSkill.getSkillValue();
            originValue += MathUtil.getLongRandom(caster.getUnitAttributeValue(AttributeType.MAGIC_ATTACK_LOWER),
                caster.getUnitAttributeValue(AttributeType.MAGIC_ATTACK_UPPER));
            MagicGroupAttack.valueOf(caster, target, baseSkill, originValue, battleParam).doActive();
            triggerBuffs(caster, targets, baseSkill);
        });

    }

}
