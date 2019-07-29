package game.base.effect.model.impl;

import game.base.buff.model.BuffContext;
import game.base.buff.model.BuffParamEnum;
import game.base.effect.model.BaseEffect;
import game.base.fight.base.model.attack.impl.PhysicalSingleAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;

/**
 * 物理中毒效果
 *
 * @author : ddv
 * @since : 2019/7/23 8:04 PM
 */
// FIXME 这里要记录相关信息
public class PoisonEffect extends BaseEffect {
    @Override
    public void active(BuffContext buffContext) {
        int level = buffContext.getParam(BuffParamEnum.POISON_LEVEL);
        long damage = buffContext.getParam(BuffParamEnum.POISON_DAMAGE);
        BaseCreatureUnit caster = buffContext.getParam(BuffParamEnum.CASTER);
        BaseSkill baseSkill = buffContext.getParam(BuffParamEnum.Skill);
        BaseCreatureUnit creatureUnit = buffContext.getParam(BuffParamEnum.Target);
        PhysicalSingleAttack attack = PhysicalSingleAttack.valueOf(caster, creatureUnit, baseSkill, level * damage,
            buffContext.getParam(BuffParamEnum.Battle_Param));
        attack.doActive();
    }

}
