package game.base.buff.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/24 6:03 PM
 */

public enum BuffTriggerPointEnum {
    /**
     * buff启动
     */
    First_Active,
    /**
     * 调度激活buff
     */
    Schedule_Active,
    /**
     * buff执行完毕
     */
    End,;

    private static Map<String, BuffTriggerPointEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (BuffTriggerPointEnum pointEnum : BuffTriggerPointEnum.values()) {
            NAME_TO_TYPE.put(pointEnum.name(), pointEnum);
        }
    }

    public static BuffTriggerPointEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }
}
