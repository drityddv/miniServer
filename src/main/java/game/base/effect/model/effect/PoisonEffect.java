package game.base.effect.model.effect;

import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;
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
        int level = buffContext.getParam(BuffContextParamEnum.POISON_LEVEL);
        long damage = buffContext.getParam(BuffContextParamEnum.POISON_DAMAGE);
        BaseCreatureUnit creatureUnit = buffContext.getParam(BuffContextParamEnum.Target);
        PhysicalSingleAttack attack = PhysicalSingleAttack.valueOf(null, creatureUnit, null, level * damage);
        attack.doActive();
    }

}
