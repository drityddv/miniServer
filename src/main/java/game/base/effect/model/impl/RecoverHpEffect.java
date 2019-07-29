package game.base.effect.model.impl;

import game.base.buff.model.BuffContext;
import game.base.buff.model.BuffParamEnum;
import game.base.effect.model.BaseEffect;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * @author : ddv
 * @since : 2019/7/24 11:04 PM
 */

public class RecoverHpEffect extends BaseEffect {

    @Override
    public void active(BuffContext buffContext) {
        BaseCreatureUnit creatureUnit = buffContext.getParam(BuffParamEnum.Target);
        long cureValue = buffContext.getParam(BuffParamEnum.CureHp);
        creatureUnit.cureHp(cureValue);
    }
}
