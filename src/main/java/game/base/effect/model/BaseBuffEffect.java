package game.base.effect.model;

import game.base.effect.model.analysis.IBuffAnalysis;
import game.base.effect.resource.EffectResource;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * 持续类buff
 *
 * @author : ddv
 * @since : 2019/7/15 12:01 PM
 */

public abstract class BaseBuffEffect extends BaseEffect<BaseCreatureUnit> implements IBuffAnalysis {
    // 周期时间
    protected long period;
    // buff释放者
    protected BaseCreatureUnit caster;
    // FIXME 要验证quartz 会不会序列化 jobData
    protected volatile boolean cancel = false;

    public void init(BaseCreatureUnit caster, BaseCreatureUnit owner, EffectResource effectResource) {
        super.init(owner, effectResource);
        this.period = effectResource.getPeriodTime();
        this.caster = caster;
    }

    @Override
    public void doParse(EffectResource effectResource) {

    }

    // buff效果 默认啥都不做
    public void active() {

    }

    // get and set
    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public BaseCreatureUnit getCaster() {
        return caster;
    }

    public void setCaster(BaseCreatureUnit caster) {
        this.caster = caster;
    }

}
