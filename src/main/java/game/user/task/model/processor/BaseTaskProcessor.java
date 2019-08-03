package game.user.task.model.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import game.user.task.constant.TaskEventType;
import game.user.task.event.TaskEvent;
import game.user.task.model.TaskCondition;
import game.user.task.model.TaskEntry;
import game.user.task.resource.TaskResource;
import game.user.task.service.TaskManager;

/**
 * 任务处理器
 *
 * @author : ddv
 * @since : 2019/8/1 5:45 PM
 */

public abstract class BaseTaskProcessor {

    private static final Map<TaskEventType, BaseTaskProcessor> PROCESSOR_MAP = new HashMap<>();
    @Autowired
    private TaskManager taskManager;

    public static BaseTaskProcessor getProcessor(TaskEventType taskEventType) {
        return PROCESSOR_MAP.get(taskEventType);
    }

    @PostConstruct
    private void init() {
        PROCESSOR_MAP.put(getType(), this);
    }

    /**
     * 处理器的种类
     *
     * @return
     */
    protected abstract TaskEventType getType();

    public TaskResource getTaskResource(long taskId) {
        return taskManager.getTaskResource(taskId);
    }

    /**
     * 尝试刷新任务进度
     *
     * @param taskEntry
     * @param taskEvent
     * @param taskPoolType
     *            1:触发池 2:进行池
     * @return
     */
    public boolean refreshTaskProcess(TaskEntry taskEntry, TaskEvent taskEvent, int taskPoolType) {
        boolean progressChange = false;
        TaskResource taskResource = taskEntry.getTaskResource();
        List<TaskCondition> finishCondList = taskResource.getFinishCondList();
        int index = 0;
        for (TaskCondition taskCondition : finishCondList) {
            if (taskCondition.getType() == taskEvent.getTaskEventType()) {
                int changeValue = getValue(taskEvent, taskCondition);
                progressChange = progressChange || taskEntry.updateProgress(index, changeValue, taskPoolType);
            }
            index++;
        }

        return progressChange;
    }

    /**
     * 获取对应任务 这次改变的进度值
     *
     * @param taskEvent
     * @param taskCondition
     * @return
     */
    protected abstract int getValue(TaskEvent taskEvent, TaskCondition taskCondition);

}
