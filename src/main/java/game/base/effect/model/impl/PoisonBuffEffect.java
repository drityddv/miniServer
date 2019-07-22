package game.base.effect.model.impl;

import java.util.List;

import game.base.effect.model.BaseBuffEffect;
import game.base.effect.resource.EffectResource;
import game.base.fight.base.model.attack.impl.PhysicalSingleAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * 物理毒 有生效次数的定期生效
 *
 * @author : ddv
 * @since : 2019/7/15 2:27 PM
 */

public class PoisonBuffEffect extends BaseBuffEffect {
    // 毒素层数
    private int currentLevel = 1;
    private long damage;

    /**
     * 合并逻辑:毒素层数累加 周期次数重置 实际伤害=层数*基础数值
     *
     * @param buffEffect
     */
    @Override
    public void merge(BaseBuffEffect buffEffect) {
        if (currentLevel == effectResource.getMaxMergeCount()) {
            return;
        }
        if (buffEffect instanceof PoisonBuffEffect) {
            PoisonBuffEffect poisonEffect = (PoisonBuffEffect)buffEffect;
            this.remainCount += poisonEffect.getPeriod();
            this.period = remainCount;
            this.mergedCount++;
            this.currentLevel += poisonEffect.currentLevel;
        }
    }

    @Override
    public void active() {
        this.remainCount--;
        targetList.forEach(creatureUnit -> {
            PhysicalSingleAttack attack =
                PhysicalSingleAttack.valueOf(caster, creatureUnit, null, damage * currentLevel);
            attack.doActive();
        });
    }

    @Override
    public void init(BaseCreatureUnit caster, List<BaseCreatureUnit> targetList, EffectResource effectResource) {
        super.init(caster, targetList, effectResource);
        damage = effectResource.getValueParam().get("value");
    }
}
