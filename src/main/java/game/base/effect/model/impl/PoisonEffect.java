package game.base.effect.model.impl;

import game.base.buff.model.BuffContext;
import game.base.buff.model.BuffParamEnum;
import game.base.effect.model.BaseEffect;
import game.base.fight.base.model.attack.impl.PhysicalSingleAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * 物理中毒效果
 *
 * @author : ddv
 * @since : 2019/7/23 8:04 PM
 */

public class PoisonEffect extends BaseEffect {
    @Override
    public void active(BuffContext buffContext) {
        int level = buffContext.getParam(BuffParamEnum.POISON_LEVEL);
        long damage = buffContext.getParam(BuffParamEnum.POISON_DAMAGE);
        BaseCreatureUnit creatureUnit = buffContext.getParam(BuffParamEnum.Target);
        PhysicalSingleAttack attack = PhysicalSingleAttack.valueOf(null, creatureUnit, null, level * damage);
        attack.doActive();
    }

}
