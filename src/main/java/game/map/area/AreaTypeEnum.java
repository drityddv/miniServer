package game.map.area;

import java.util.HashMap;
import java.util.Map;

import game.map.area.impl.DefaultProcess;
import game.map.area.impl.RoundAreaProcess;

/**
 * aoe范围计算枚举
 *
 * @author : ddv
 * @since : 2019/7/22 12:10 PM
 */

public enum AreaTypeEnum {
    // 圆形
    Round(1, "圆形范围", RoundAreaProcess.class),
    // 地图计算
    Default(2, "默认通过地图计算", DefaultProcess.class),;
    public static Map<Integer, AreaTypeEnum> ID_TO_TYPE = new HashMap<>();
    public static Map<String, AreaTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (AreaTypeEnum anEnum : AreaTypeEnum.values()) {
            ID_TO_TYPE.put(anEnum.id, anEnum);
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
    }

    private int id;
    private String typeName;
    private Class<? extends BaseUnitCollector> processClazz;
    private BaseUnitCollector process;

    AreaTypeEnum(int id, String typeName, Class<? extends BaseUnitCollector> processClazz) {
        this.id = id;
        this.typeName = typeName;
        this.processClazz = processClazz;
        this.create();
    }

    public static AreaTypeEnum getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    public static AreaTypeEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public int getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public BaseUnitCollector getProcess() {
        return process;
    }

    public void create() {
        try {
            process = processClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
