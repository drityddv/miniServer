package game.base.effect.model.effect;

import java.util.List;

import game.base.effect.model.buff.BaseCreatureBuff;
import game.base.effect.resource.EffectResource;
import game.base.fight.base.model.attack.impl.PhysicalSingleAttack;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import utils.TimeUtil;

/**
 * 物理中毒效果
 *
 * @author : ddv
 * @since : 2019/7/23 8:04 PM
 */

public class PoisonEffect extends BaseEffect {
    // 毒素层数
    private int currentLevel = 1;
    private long damage;

    @Override
    public void active() {
        targetList.forEach(creatureUnit -> {
            PhysicalSingleAttack attack =
                PhysicalSingleAttack.valueOf(buff.getCaster(), creatureUnit, null, damage * currentLevel);
            attack.doActive();
        });
    }

    @Override
    public void init(BaseCreatureBuff buff, List<BaseCreatureUnit> targetList, EffectResource effectResource) {
        super.init(buff, targetList, effectResource);
        damage = effectResource.getValueParam().get("value");
        nextActiveAt = (long)(TimeUtil.now() + effectResource.getFrequencyTime());
    }

	@Override
	public void merge(BaseEffect baseEffect1) {

	}
}
