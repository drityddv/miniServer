package game.base.effect.model.buff;

import java.util.List;
import java.util.Map;

import game.base.effect.model.effect.BaseEffect;
import game.base.fight.model.buff.PVPBuffEffectComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.handler.AbstractMapHandler;
import spring.SpringContext;
import utils.MathUtil;

/**
 * 持续类buff
 *
 * @author : ddv
 * @since : 2019/7/15 12:01 PM
 */

public abstract class BaseCreatureBuff extends BaseBuff<BaseCreatureUnit> {
    // buff实际调度频率
    private long frequencyTime;
    // 实际调度次数
    private int period;

    @Override
    public void init(List<BaseEffect> effectList, BaseCreatureUnit caster, List<BaseCreatureUnit> targetList) {
        super.init(effectList, caster, targetList);
        frequencyTime = getBuffFrequency();
    }

    public void active() {
        effectList.forEach(BaseEffect::active);
    }

    /**
     * 初始化buffJob
     */
    public abstract void initBuffJob();

    /**
     * 注册调度相关
     */
    public void schedule() {
        SpringContext.getQuartzService().scheduleJob(buffJob.getJobEntry());
    }

    // get and set
    public void setCaster(BaseCreatureUnit caster) {
        this.caster = caster;
    }

    public Map<Long, BaseCreatureBuff> getMapBuffGroup() {
        return AbstractMapHandler.getAbstractMapHandler(caster.getMapId()).getBuffEffects(caster.getMapId());
    }

    // buff实际调度频率
    public long getBuffFrequency() {
        long result = 1000;
        for (BaseEffect baseEffect : effectList) {
            if (baseEffect.isScheduleEffect()) {
                result = MathUtil.getGcd(result, baseEffect.getFrequencyTime());
            }
        }
        return result;
    }

    public int getBuffPeriod(){
    	long maxPeriod = Long.MIN_VALUE;
		for (BaseEffect baseEffect : effectList) {
			maxPeriod = Math.max(maxPeriod,baseEffect.get)
		}
	}

    // buff失效
    public void removeBuff() {
        targetList.forEach(creatureUnit -> {
            PVPBuffEffectComponent component =
                creatureUnit.getComponentContainer().getComponent(UnitComponentType.BUFF);
            component.removeBuff(this);
        });
    }

}
