package game.user.task.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import game.user.task.constant.TaskConst;
import game.user.task.constant.TaskEventType;
import game.user.task.resource.TaskResource;
import game.user.task.service.TaskManager;

/**
 * @author : ddv
 * @since : 2019/8/1 11:49 PM
 */

public class TaskEntry {
    private long taskId;
    private transient TaskResource taskResource;

    // 进度下标根据taskResource的finishCondList下标 依次对应
    // 完成需要达到的进度
    private Map<Integer, Integer> totalProgress = new HashMap<>();

    private Map<Integer, Integer> executingProgress = new HashMap<>();

    // 触发进度
    private Map<Integer, Integer> triggerProgress = new HashMap<>();

    public static TaskEntry valueOf(long taskId) {
        TaskEntry taskEntry = new TaskEntry();
        taskEntry.taskId = taskId;
        taskEntry.taskResource = TaskManager.getInstance().getTaskResource(taskId);
        List<Integer> finishProcessor = taskEntry.taskResource.getFinishProcessor();
        for (int i = 0; i < finishProcessor.size(); i++) {
            taskEntry.executingProgress.put(i, 0);
            taskEntry.triggerProgress.put(i, 0);
            taskEntry.totalProgress.put(i, finishProcessor.get(i));
        }
        return taskEntry;
    }

    public static TaskEntry valueOf(TaskEntry taskEntry) {
        TaskEntry newTaskEntry = new TaskEntry();
        newTaskEntry.taskId = taskEntry.taskId;
        newTaskEntry.taskResource = taskEntry.taskResource;
        newTaskEntry.totalProgress = new HashMap<>(taskEntry.totalProgress);
        newTaskEntry.executingProgress = new HashMap<>(taskEntry.executingProgress);
        newTaskEntry.triggerProgress = new HashMap<>(taskEntry.triggerProgress);
        return newTaskEntry;
    }

    public long getTaskId() {
        return taskId;
    }

    public TaskResource getTaskResource() {
        return taskResource;
    }

    public void setTaskResource(TaskResource taskResource) {
        this.taskResource = taskResource;
    }

    public Map<Integer, Integer> getExecutingProgress() {
        return executingProgress;
    }

    public boolean containsTaskEventType(TaskEventType eventType) {
        return getTaskEventTypeSet().contains(eventType);
    }

    public Set<TaskEventType> getTaskEventTypeSet() {
        return taskResource.getTaskEventTypeSet();
    }

    public Map<Integer, Integer> getTriggerProgress() {
        return triggerProgress;
    }

    /**
     * 尝试修改任务进度
     *
     * @param index
     * @param changeValue
     * @return
     */
    public boolean updateProgress(int index, int changeValue, int poolType) {
        if (changeValue == 0) {
            return false;
        }
        if (poolType == TaskConst.Trigger_Pool) {

            if (!triggerProgress.containsKey(index)) {
                return false;
            }

            int currentValue = triggerProgress.get(index);
            triggerProgress.put(index, currentValue + changeValue);
            return true;
        }

        if (!executingProgress.containsKey(index)) {
            return false;
        }

        int currentValue = executingProgress.get(index);
        executingProgress.put(index, currentValue + changeValue);
        return true;
    }

    public void log(Logger logger) {
        logger.info("任务[{}] ", taskId);
        totalProgress.forEach((index, progress) -> {
            logger.info("子阶段[{}] 总进度[{}] 进行池进度[{}] 触发池进度[{}]", index, progress, executingProgress.get(index),
                triggerProgress.get(index));
        });

    }
}
