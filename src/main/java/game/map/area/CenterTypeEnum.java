package game.map.area;

import java.util.HashMap;
import java.util.Map;

/**
 * 中心点类型
 *
 * @author : ddv
 * @since : 2019/7/26 2:14 PM
 */

public enum CenterTypeEnum {
    /**
     * 指定中心点
     */
    Target_Grid(1),
    /**
     * 自身为中心点
     */
    Self(2),
    /**
     * 默认 根据id查找
     */
    Default(3),;

    private static Map<Integer, CenterTypeEnum> ID_TO_TYPE = new HashMap<>();
    private static Map<String, CenterTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (CenterTypeEnum anEnum : CenterTypeEnum.values()) {
            ID_TO_TYPE.put(anEnum.id, anEnum);
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
    }

    private int id;

    CenterTypeEnum(int id) {
        this.id = id;
    }

    public static CenterTypeEnum getById(Integer id) {
        CenterTypeEnum centerTypeEnum = ID_TO_TYPE.get(id);
        if (centerTypeEnum == null) {
            return Default;
        }
        return centerTypeEnum;
    }

    public static CenterTypeEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public int getId() {
        return id;
    }
}
