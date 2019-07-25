package game.base.buff.model;

import java.util.HashMap;
import java.util.Map;

import game.base.buff.model.impl.AvatarCycleBuff;
import game.base.buff.model.impl.PoisonCycleBuff;

/**
 * @author : ddv
 * @since : 2019/7/23 8:24 PM
 */

public enum BuffTypeEnum {
    /**
     * 周期毒buff
     */
    Poison_Cycle_Buff(1, PoisonCycleBuff.class),
    /**
     * 天神下凡
     */
    Avatar_Buff(2, AvatarCycleBuff.class),;

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

    public BaseCreatureBuff create() {
        try {
            return buffClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
