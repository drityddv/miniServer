package game.base.effect.model.buff;

import java.util.List;

import game.base.effect.model.effect.BaseEffect;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import scheduler.job.model.BuffJob;

/**
 * 基础效果[支持合并,过期]
 *
 * @author : ddv
 * @since : 2019/7/15 10:39 AM
 */

public abstract class BaseBuff<T> {
    // buff调度作业
    protected BuffJob buffJob;
    // 效果集合
    protected List<BaseEffect> effectList;
    // buff释放者
    protected BaseCreatureUnit caster;
    // buff作用对象
    protected List<T> targetList;
    // 当前合并次数
    protected long mergedCount;

    public void init(List<BaseEffect> effectList, BaseCreatureUnit caster, List<T> targetList) {
        this.effectList = effectList;
        this.caster = caster;
        this.targetList = targetList;
    }

    /**
     * 效果合并
     *
     * @param buffEffect
     */
    public void merge(BaseCreatureBuff buffEffect) {
        effectList.forEach(baseEffect -> {
            buffEffect.getEffectList().forEach(baseEffect1 -> {
                baseEffect.merge(baseEffect1);
            });
        });
    }

    public BuffJob getBuffJob() {
        return buffJob;
    }

    public List<BaseEffect> getEffectList() {
        return effectList;
    }

    public BaseCreatureUnit getCaster() {
        return caster;
    }

    public List<T> getTargetList() {
        return targetList;
    }

    public long getMergedCount() {
        return mergedCount;
    }

}
