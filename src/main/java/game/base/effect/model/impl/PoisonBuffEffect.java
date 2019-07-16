package game.base.effect.model.impl;

import game.base.effect.model.BaseBuffEffect;
import game.base.effect.model.constant.EffectOperationType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * @author : ddv
 * @since : 2019/7/15 2:27 PM
 */

public class PoisonBuffEffect extends BaseBuffEffect {
    // 毒素层数
    private int currentLevel;

    /**
     * 合并逻辑:毒素层数累加
     *
     * @param buffEffect
     */
    @Override
    public void merge(BaseBuffEffect buffEffect) {
        if (buffEffect instanceof PoisonBuffEffect) {
            PoisonBuffEffect poisonEffect = (PoisonBuffEffect)buffEffect;
            this.currentLevel += poisonEffect.currentLevel;
        }
    }

    @Override
    protected void active(BaseCreatureUnit unit, EffectOperationType type) {
        // 扣血待做
        super.active(unit, type);
    }
}
