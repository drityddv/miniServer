package game.base.buff.model;

import java.util.*;

import game.base.buff.model.impl.AvatarBuff;
import game.base.buff.model.impl.PoisonScheduleBuff;

/**
 * @author : ddv
 * @since : 2019/7/23 8:24 PM
 */

public enum BuffTypeEnum {
    /**
     * 周期毒buff
     */
    Poison_Cycle_Buff(1, PoisonScheduleBuff.class) {
        @Override
        public Set<BuffTriggerPointEnum> getTriggerPointSet() {
            return new HashSet<>(
                Arrays.asList(BuffTriggerPointEnum.First_Active, BuffTriggerPointEnum.Schedule_Active));
        }
    },
    /**
     * 天神下凡
     */
    Avatar_Buff(2, AvatarBuff.class) {
        @Override
        public Set<BuffTriggerPointEnum> getTriggerPointSet() {
            return new HashSet<>(Arrays.asList(BuffTriggerPointEnum.First_Active, BuffTriggerPointEnum.Schedule_Active,
                BuffTriggerPointEnum.End));
        }
    },;

    private static Map<Integer, BuffTypeEnum> ID_TO_TYPE = new HashMap<>();
    private static Map<String, BuffTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (BuffTypeEnum anEnum : BuffTypeEnum.values()) {
            ID_TO_TYPE.put(anEnum.id, anEnum);
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
    }

    private int id;
    private Class<? extends BaseCreatureBuff> buffClazz;
    private Set<BuffTriggerPointEnum> triggerPointSet = new HashSet<>();

    BuffTypeEnum(int id, Class<? extends BaseCreatureBuff> buffClazz) {
        this.id = id;
        this.buffClazz = buffClazz;
    }

    public static BuffTypeEnum getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    public static BuffTypeEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public Set<BuffTriggerPointEnum> getTriggerPointSet() {
        return new HashSet<>();
    }

    public BaseCreatureBuff create() {
        try {
            return buffClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
