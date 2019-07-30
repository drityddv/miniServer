package quartz.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * quartz组名枚举
 *
 * @author : ddv
 * @since : 2019/7/9 下午5:49
 */

public enum JobGroupEnum {
    /**
     * 公共服务
     */
    PUBLIC_COMMON(),

    /**
     * 地图定时调度[地图心跳]
     */
    SCENE_COMMON_RATE(),
    /**
     * 场景复活任务
     */
    SCENE_RELIVE(),
    /**
     * buff定时服务
     */
    BUFF(),
    /**
     * 测试用
     */
    TEST(),;

    public static Map<String, JobGroupEnum> NAME_TO_GROUP = new HashMap<>();

    static {
        for (JobGroupEnum groupEnum : JobGroupEnum.values()) {
            NAME_TO_GROUP.put(groupEnum.name(), groupEnum);
        }
    }

    public static JobGroupEnum getByName(String name) {
        return NAME_TO_GROUP.get(name);
    }
}
