package game.base.effect.model.constant;

import java.util.Set;

import game.base.effect.model.BaseBuffEffect;
import game.base.effect.model.analysis.IBuffAnalysis;
import game.base.effect.model.impl.DizzyBuffEffect;

/**
 * @author : ddv
 * @since : 2019/7/15 11:15 AM
 */

public enum EffectType {
    /**
     * 眩晕
     */
    DIZZY(DizzyBuffEffect.class),;

    private Class<? extends BaseBuffEffect> buffClazz;

    private Class<? extends IBuffAnalysis> buffBeanClazz;

    private Set<RestrictStatusEnum> restrictStatus;

    EffectType(Class<? extends BaseBuffEffect> buffClazz) {
        this.buffClazz = buffClazz;
    }

    EffectType(Class<? extends BaseBuffEffect> buffClazz, Class<? extends IBuffAnalysis> buffBeanClazz) {
        this.buffClazz = buffClazz;
        this.buffBeanClazz = buffBeanClazz;
    }

    EffectType(Class<? extends BaseBuffEffect> buffClazz, Class<? extends IBuffAnalysis> buffBeanClazz,
        Set<RestrictStatusEnum> restrictStatus) {
        this.buffClazz = buffClazz;
        this.buffBeanClazz = buffBeanClazz;
        this.restrictStatus = restrictStatus;
    }

    public BaseBuffEffect create() {
        try {
            return this.buffClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Class<? extends BaseBuffEffect> getBuffClazz() {
        return buffClazz;
    }

    public Class<? extends IBuffAnalysis> getBuffBeanClazz() {
        return buffBeanClazz;
    }

    public Set<RestrictStatusEnum> getRestrictStatus() {
        return restrictStatus;
    }
}
