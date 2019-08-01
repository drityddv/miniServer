package game.world.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/8/1 5:09 PM
 */

public enum CreatureTypeEnum {
    /**
     * 骷髅士兵
     */
    Skeleton_Soldier(1),
    /**
     * 骷髅法师
     */
    Skeleton_Mage(2),
    /**
     * 骷髅弓箭手
     */
    Skeleton_Archer(3),;

    private static Map<Integer, CreatureTypeEnum> ID_TO_TYPE = new HashMap<>();

    static {
        for (CreatureTypeEnum typeEnum : CreatureTypeEnum.values()) {
            ID_TO_TYPE.put(typeEnum.id, typeEnum);
        }
    }

    private int id;

    CreatureTypeEnum(int id) {
        this.id = id;
    }

    public static CreatureTypeEnum getById(int id) {
        return ID_TO_TYPE.get(id);
    }
}
