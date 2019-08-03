package game.user.task.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/8/2 6:25 PM
 */

public enum TaskType {
    // 主线任务
    Main_Line(1),
    // 分线任务
    Split_Line(2),;

    private static Map<Integer, TaskType> ID_TO_TYPE = new HashMap<>();

    static {
        for (TaskType taskType : TaskType.values()) {
            ID_TO_TYPE.put(taskType.id, taskType);
        }
    }

    private int id;

    TaskType(int id) {
        this.id = id;
    }

    public static TaskType getById(int id) {
        return ID_TO_TYPE.get(id);
    }
}
