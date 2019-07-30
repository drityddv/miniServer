package game.base.fight.model.skill.action.handler.impl;

import java.util.List;

import game.base.fight.base.model.attack.impl.MagicSingleAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.skill.model.BaseSkill;
import game.world.fight.model.BattleParam;

/**
 * 寒冰箭
 *
 * @author : ddv
 * @since : 2019/7/16 10:24 AM
 */

public class FrostBolt extends BaseActionHandler {

    @Override
    protected void doAction(BaseCreatureUnit caster, List<BaseCreatureUnit> targets, BaseSkill baseSkill,
        BattleParam battleParam) {
        if (targets == null || targets.size() != 1) {
            logger.warn("使用寒冰箭失败,targets参数长度不为1");
            return;
        }

        long value = BattleUtil.calculateSkillValue1(baseSkill.getSkillLevelResource().getValue(),
            baseSkill.getSkillLevelResource().getAttributeTypes(), caster.getAttributeComponent().getFinalAttributes());
        BaseCreatureUnit target = targets.get(0);

        MagicSingleAttack.valueOf(caster, target, baseSkill, value, battleParam).doActive();
        triggerBuffs(caster, targets, baseSkill);
    }

}
