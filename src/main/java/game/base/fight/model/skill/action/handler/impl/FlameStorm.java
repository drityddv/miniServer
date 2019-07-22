package game.base.fight.model.skill.action.handler.impl;

import game.base.fight.base.model.attack.impl.MagicGroupAttack;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.game.attribute.AttributeType;

/**
 * 烈焰风暴
 *
 * @author : ddv
 * @since : 2019/7/22 5:33 PM
 */

public class FlameStorm extends BaseActionHandler {

    @Override
    protected void doAction() {
        long originValue = getSkillValue();
        originValue +=
            BattleUtil.getLongRandom(BattleUtil.getUnitAttributeValue(caster, AttributeType.PHYSICAL_ATTACK_LOWER),
                BattleUtil.getUnitAttributeValue(caster, AttributeType.PHYSICAL_ATTACK_UPPER));
        MagicGroupAttack attack = MagicGroupAttack.valueOf(caster, defenders, baseSkill, originValue);
        attack.doActive();
        super.doAction();
    }
}
