package game.base.effect.model.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import game.base.effect.model.effect.BaseEffect;
import game.base.effect.model.effect.DizzyEffect;
import game.base.effect.model.effect.PoisonEffect;

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
    Dizzy(1, DizzyEffect.class),
    /**
     * 毒素
     */
    Poison(2, PoisonEffect.class);

    private static Map<Long, EffectTypeEnum> ID_TO_TYPE = new HashMap<>();
    private static Map<String, EffectTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (EffectTypeEnum type : EffectTypeEnum.values()) {
            ID_TO_TYPE.put(type.typeId, type);
            NAME_TO_TYPE.put(type.name(), type);
        }
    }

    private long typeId;
    private Class<? extends BaseEffect> effectClazz;
    private Set<RestrictStatusEnum> restrictStatus;

    EffectTypeEnum(int typeId, Class<? extends BaseEffect> effectClazz) {
        this.typeId = typeId;
        this.effectClazz = effectClazz;
    }

    public static EffectTypeEnum getById(long typeId) {
		return ID_TO_TYPE.get(typeId);
	}

    public BaseEffect create() {
        try {
            return effectClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<? extends BaseEffect> getEffectClazz() {
        return effectClazz;
    }

    public Set<RestrictStatusEnum> getRestrictStatus() {
        return restrictStatus;
    }
}
