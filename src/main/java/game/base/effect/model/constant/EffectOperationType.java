package game.base.effect.model.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 效果操作类型
 *
 * @author : ddv
 * @since : 2019/7/15 2:36 PM
 */

public enum EffectOperationType {
    /**
     * 合并
     */
    MERGE(1),
    /**
     * 覆盖
     */
    COVER(2),;

    private static Map<Integer, EffectOperationType> ID_TO_TYPE = new HashMap<>();
    private static Map<String, EffectOperationType> NAME_TO_TYPE = new HashMap<>();

    static {
        for (EffectOperationType type : EffectOperationType.values()) {
            ID_TO_TYPE.put(type.value, type);
            NAME_TO_TYPE.put(type.name(), type);
        }
    }

    private int value;

    EffectOperationType(int value) {
        this.value = value;
    }

    public static EffectOperationType getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public static EffectOperationType getById(int id) {
        return ID_TO_TYPE.get(id);
    }
}
