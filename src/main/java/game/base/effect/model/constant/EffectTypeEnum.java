package game.base.effect.model.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import game.base.effect.model.BaseBuffEffect;
import game.base.effect.model.analysis.IBuffAnalysis;
import game.base.effect.model.impl.DizzyBuffEffect;
import game.base.effect.model.impl.PoisonBuffEffect;

/**
 * 效果枚举
 *
 * @author : ddv
 * @since : 2019/7/15 11:15 AM
 */

public enum EffectTypeEnum {
    /**
     * 眩晕
     */
    Dizzy(1, DizzyBuffEffect.class),
    /**
     * 毒素
     */
    Poison(2, PoisonBuffEffect.class);

    private static Map<Integer, EffectTypeEnum> ID_TO_TYPE = new HashMap<>();
    private static Map<String, EffectTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (EffectTypeEnum type : EffectTypeEnum.values()) {
            ID_TO_TYPE.put(type.typeId, type);
            NAME_TO_TYPE.put(type.name(), type);
        }
    }

    private int typeId;
    private Class<? extends BaseBuffEffect> buffClazz;
    private Class<? extends IBuffAnalysis> buffBeanClazz;
    private Set<RestrictStatusEnum> restrictStatus;

    EffectTypeEnum(int typeId, Class<? extends BaseBuffEffect> buffEffectClass) {
        this.typeId = typeId;
        this.buffClazz = buffClazz;
    }

    public static EffectTypeEnum getById(int typeId) {
        return ID_TO_TYPE.get(typeId);
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
