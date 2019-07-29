package game.base.effect.model.constant;

import java.util.*;

import game.base.buff.model.BuffTriggerPoint;
import game.base.effect.model.BaseEffect;
import game.base.effect.model.impl.*;

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
    Poison(2, PoisonEffect.class) {
        @Override
        public Set<BuffTriggerPoint> getTriggerPointSet() {
            return new HashSet<>(Arrays.asList(BuffTriggerPoint.Schedule_Active));
        }
    },

    Plus_Buff_Attribute(3, PlusBuffAttrEffect.class) {
        @Override
        public Set<BuffTriggerPoint> getTriggerPointSet() {
            return new HashSet<>(Arrays.asList(BuffTriggerPoint.First_Active, BuffTriggerPoint.End));
        }
    },

    Recover_Hp(4, RecoverHpEffect.class) {
        @Override
        public Set<BuffTriggerPoint> getTriggerPointSet() {
            return new HashSet<>(Arrays.asList(BuffTriggerPoint.Schedule_Active));
        }
    },

    Remove_Buff_Attribute(5, RemoveBuffAttrEffect.class) {
        @Override
        public Set<BuffTriggerPoint> getTriggerPointSet() {
            return new HashSet<>(Arrays.asList(BuffTriggerPoint.End));
        }
    };

    private static Map<Long, EffectTypeEnum> ID_TO_TYPE = new HashMap<>();
    private static Map<String, EffectTypeEnum> NAME_TO_TYPE = new HashMap<>();
    private static Map<Class<? extends BaseEffect>, EffectTypeEnum> ClASS_TO_TYPE = new HashMap<>();

    static {
        for (EffectTypeEnum type : EffectTypeEnum.values()) {
            ID_TO_TYPE.put(type.typeId, type);
            NAME_TO_TYPE.put(type.name(), type);
            ClASS_TO_TYPE.put(type.effectClazz, type);
        }
    }

    private long typeId;
    private Class<? extends BaseEffect> effectClazz;
    private Set<BuffTriggerPoint> triggerPointSet = new HashSet<>();

    EffectTypeEnum(int typeId, Class<? extends BaseEffect> effectClazz) {
        this.typeId = typeId;
        this.effectClazz = effectClazz;
    }

    public static EffectTypeEnum getById(long typeId) {
        return ID_TO_TYPE.get(typeId);
    }

    public static EffectTypeEnum getByClazz(Class<? extends BaseEffect> typeClazz) {
        return ClASS_TO_TYPE.get(typeClazz);
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

    public Set<BuffTriggerPoint> getTriggerPointSet() {
        return new HashSet<>();
    }
}
