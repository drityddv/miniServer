package game.base.fight.model.skill.action.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.base.model.attack.impl.MagicSingleAttack;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;

/**
 * 寒冰箭
 *
 * @author : ddv
 * @since : 2019/7/16 10:24 AM
 */

public class FrostBolt extends BaseActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(FrostBolt.class);

    @Override
    protected void doAction() {
        super.doAction();

        long value = BattleUtil.calculateSkillValue1(baseSkill.getSkillLevelResource().getValue(),
            baseSkill.getSkillLevelResource().getAttributeTypes(),
            BattleUtil.getUnitAttrComponent(caster).getFinalAttributes());

        MagicSingleAttack attack = MagicSingleAttack.valueOf(caster, defender, baseSkill, value);
        attack.doActive();
    }
}
