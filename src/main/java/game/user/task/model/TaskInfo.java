package game.user.task.model;

import java.util.*;
import java.util.stream.Collectors;

import game.user.task.constant.TaskEventType;
import game.user.task.service.TaskManager;

/**
 * 任务模型
 *
 * @author : ddv
 * @since : 2019/8/1 5:05 PM
 */

public class TaskInfo {
    private transient boolean loaded = false;
    // 完成的任务id集合
    private Set<Long> finishedTaskIdList = new HashSet<>();
    // 正在进行中的任务
    private Map<Long, TaskEntry> executingTaskMap = new HashMap<>();
    // 触发流程中的任务
    private Map<Long, TaskEntry> triggerTaskMap = new HashMap<>();

    public static TaskInfo valueOf() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.firstInit();
        return taskInfo;
    }

    private void firstInit() {
        TaskEntry taskEntry = TaskEntry.valueOf(1);
        putExecutingTask(taskEntry);
    }

    public Set<Long> getFinishedTaskIdList() {
        return finishedTaskIdList;
    }

    public Map<Long, TaskEntry> getExecutingTaskMap() {
        return executingTaskMap;
    }

    public Map<Long, TaskEntry> getTriggerTaskMap() {
        return triggerTaskMap;
    }

    // methods
    public List<TaskEntry> getExecutingTaskByEventType(TaskEventType type) {
        List<TaskEntry> taskEntryList = new ArrayList<>(executingTaskMap.values().stream()
            .filter(taskEntry -> taskEntry.containsTaskEventType(type)).collect(Collectors.toList()));
        return taskEntryList;
    }

    public List<TaskEntry> getTriggerTaskByEventType(TaskEventType type) {
        List<TaskEntry> taskEntryList = new ArrayList<>(triggerTaskMap.values().stream()
            .filter(taskEntry -> taskEntry.containsTaskEventType(type)).collect(Collectors.toList()));
        return taskEntryList;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void load() {
        executingTaskMap.values().forEach(taskEntry -> {
            taskEntry.setTaskResource(TaskManager.getInstance().getTaskResource(taskEntry.getTaskId()));
        });
        triggerTaskMap.values().forEach(taskEntry -> {
            taskEntry.setTaskResource(TaskManager.getInstance().getTaskResource(taskEntry.getTaskId()));
        });
        loaded = true;
    }

    public void removeTriggerTask(long taskId) {
        triggerTaskMap.remove(taskId);
    }

    public void removeExecutingTask(long taskId) {
        executingTaskMap.remove(taskId);
    }

    public TaskEntry getTriggerTask(long taskId) {
        return triggerTaskMap.get(taskId);
    }

    public TaskEntry getExecutingTask(long taskId) {
        return executingTaskMap.get(taskId);
    }

    public void putExecutingTask(TaskEntry taskEntry) {
        executingTaskMap.put(taskEntry.getTaskId(), taskEntry);
    }

    public void putTriggerTask(TaskEntry taskEntry) {
        if (triggerTaskMap.containsKey(taskEntry.getTaskId())) {
            return;
        }
        triggerTaskMap.put(taskEntry.getTaskId(), taskEntry);
    }

    public void addFinishTaskId(long taskId) {
        finishedTaskIdList.add(taskId);
    }
}
