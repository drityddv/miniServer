package game.base.effect.model;

import game.base.effect.model.constant.EffectOperationType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * 持续类buff
 *
 * @author : ddv
 * @since : 2019/7/15 12:01 PM
 */

public abstract class BaseBuffEffect extends BaseEffect<BaseCreatureUnit> {
    // 周期时间
    protected long periodTime;
    // buff获得时间
    protected long gainTime;
    // buff释放者
    protected BaseCreatureUnit caster;

    // 效果激活
    protected void active(BaseCreatureUnit unit, EffectOperationType type) {

    }

    // get and set
    public long getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(long periodTime) {
        this.periodTime = periodTime;
    }

    public long getGainTime() {
        return gainTime;
    }

    public void setGainTime(long gainTime) {
        this.gainTime = gainTime;
    }

    public BaseCreatureUnit getCaster() {
        return caster;
    }

    public void setCaster(BaseCreatureUnit caster) {
        this.caster = caster;
    }

}
