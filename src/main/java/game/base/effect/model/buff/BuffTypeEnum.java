package game.base.effect.model.buff;

import java.util.HashMap;
import java.util.Map;

import game.base.effect.model.buff.impl.CycleActiveBuff;

/**
 * @author : ddv
 * @since : 2019/7/23 8:24 PM
 */

public enum BuffTypeEnum {
    /**
     * 周期执行的buff
     */
    Cycle_Active(1, CycleActiveBuff.class),;

    BuffTypeEnum(int id, Class<? extends BaseCreatureBuff> buffClazz) {
        this.id = id;
        this.buffClazz = buffClazz;
    }

    private int id;
    private Class<? extends BaseCreatureBuff> buffClazz;
    private static Map<Integer, BuffTypeEnum> ID_TO_TYPE = new HashMap<>();
    private static Map<String, BuffTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (BuffTypeEnum anEnum : BuffTypeEnum.values()) {
            ID_TO_TYPE.put(anEnum.id, anEnum);
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
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
