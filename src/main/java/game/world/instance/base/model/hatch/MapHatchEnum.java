package game.world.instance.base.model.hatch;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/31 9:58 AM
 */

public enum MapHatchEnum {
    /**
     * 怪物孵化器
     */
    Monster_Hatch(1, new MonsterHatch()),;

    private static Map<Integer, MapHatchEnum> ID_TO_TYPE = new HashMap<>();

    static {
        for (MapHatchEnum hatchEnum : MapHatchEnum.values()) {
            ID_TO_TYPE.put(hatchEnum.hatchId, hatchEnum);
        }
    }

    private int hatchId;
    private BaseMapObjectHatch hatch;

    MapHatchEnum(int hatchId, BaseMapObjectHatch hatch) {
        this.hatchId = hatchId;
        this.hatch = hatch;
    }

    public static MapHatchEnum getById(int hatchId) {
        return ID_TO_TYPE.get(hatchId);
    }

    public BaseMapObjectHatch getHatch() {
        return hatch;
    }
}
