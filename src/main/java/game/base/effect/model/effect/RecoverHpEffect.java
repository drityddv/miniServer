package game.base.effect.model.effect;

import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * @author : ddv
 * @since : 2019/7/24 11:04 PM
 */

public class RecoverHpEffect extends BaseEffect {

    @Override
    public void active(BuffContext buffContext) {
        BaseCreatureUnit creatureUnit = buffContext.getParam(BuffContextParamEnum.Target);
        long cureValue = buffContext.getParam(BuffContextParamEnum.CureHp);
        creatureUnit.cureHp(cureValue);
    }
}
