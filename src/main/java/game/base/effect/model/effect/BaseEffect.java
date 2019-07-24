package game.base.effect.model.effect;

import java.util.List;

import game.base.effect.model.buff.BaseCreatureBuff;
import game.base.effect.resource.EffectResource;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * @author : ddv
 * @since : 2019/7/23 5:56 PM
 */

public abstract class BaseEffect {

    protected BaseCreatureBuff buff;

    protected List<BaseCreatureUnit> targetList;

    // 执行频率
    protected long frequencyTime;

    protected long nextActiveAt;

    protected EffectResource effectResource;

    /**
     * 效果生效
     */
    public abstract void active();

    /**
     * 初始化
     *
     * @param buff
     * @param targetList
     * @param effectResource
     */
    public void init(BaseCreatureBuff buff, List<BaseCreatureUnit> targetList, EffectResource effectResource) {
        this.buff = buff;
        this.targetList = targetList;
        this.frequencyTime = effectResource.getFrequencyTime();
        this.effectResource = effectResource;
    }

    /**
     * 效果合并
     *
     * @param baseEffect1
     */
    public abstract void merge(BaseEffect baseEffect1);

    //实际调度次数
    public int getScheduleTime(){
    	return effectResource.getPeriodTime();
	}

    // 实际调度频率
    public long getFrequency() {
        if (effectResource.getFrequencyTime() > 0) {
            return effectResource.getFrequencyTime();
        }
        return effectResource.getPeriodTime();
    }

    // 是否是需要调度的效果[延时,周期..]
    public boolean isScheduleEffect() {
        if (effectResource.getFrequencyTime() > 0) {
            return true;
        }
        if (effectResource.getPeriodTime() > 0) {
            return true;
        }
        return false;
    }

    public BaseCreatureBuff getBuff() {
        return buff;
    }

    public void setBuff(BaseCreatureBuff buff) {
        this.buff = buff;
    }

    public List<BaseCreatureUnit> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<BaseCreatureUnit> targetList) {
        this.targetList = targetList;
    }

    public long getFrequencyTime() {
        return frequencyTime;
    }

    public void setFrequencyTime(long frequencyTime) {
        this.frequencyTime = frequencyTime;
    }

    public long getNextActiveAt() {
        return nextActiveAt;
    }

    public void setNextActiveAt(long nextActiveAt) {
        this.nextActiveAt = nextActiveAt;
    }

    public EffectResource getEffectResource() {
        return effectResource;
    }

    public void setEffectResource(EffectResource effectResource) {
        this.effectResource = effectResource;
    }
}
