package game.user.task.model;

import java.util.ArrayList;
import java.util.List;

import game.user.task.constant.TaskEventType;

/**
 * @author : ddv
 * @since : 2019/8/2 10:22 AM
 */

public class TaskCondition {

    private TaskEventType type;
    // decoder self
    private List<Object> paramList = new ArrayList<>();

    private int totalProgress;

    public TaskEventType getType() {
        return type;
    }

    public void setType(TaskEventType type) {
        this.type = type;
    }

    public void addParam(Object param) {
        paramList.add(param);
    }

    public <T> T getParam(int index) {
        return (T)paramList.get(index);
    }

    public void loadProgress() {
        totalProgress = type.getProgress(paramList);
    }

    public int getTotalProgress() {
        return totalProgress;
    }
}
