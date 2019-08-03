package game.user.task.model.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.role.player.event.PlayerLevelUpEvent;
import game.role.player.model.Player;
import game.user.task.constant.TaskConst;
import game.user.task.constant.TaskEventType;
import game.user.task.event.TaskEvent;
import game.user.task.model.TaskEntry;
import game.user.task.model.TaskInfo;
import game.user.task.service.TaskManager;
import spring.SpringContext;

/**
 * 任务的事件接受器
 *
 * @author : ddv
 * @since : 2019/8/2 12:02 PM
 */
@Component
public class TaskEventHandler {

    @Autowired
    private TaskManager taskManager;

    public void doLevelUp(PlayerLevelUpEvent event) {
        SpringContext.getEventBus().pushEventSyn(TaskEvent.valueOf(event.getPlayer(), TaskEventType.Player_LevelUp));
    }

    /**
     * 处理任务事件
     *
     * @param taskEvent
     */
    public void handler(TaskEvent taskEvent) {
        Player player = taskEvent.getPlayer();
        TaskInfo taskInfo = player.getTaskInfo();
        TaskEventType eventType = taskEvent.getTaskEventType();

        // 根据type获取任务处理器处理
        BaseTaskProcessor taskProcessor = BaseTaskProcessor.getProcessor(eventType);

        // 处理触发池任务
        List<TaskEntry> triggerTaskList = taskInfo.getTriggerTaskByEventType(eventType);
        for (TaskEntry taskEntry : triggerTaskList) {
            if (taskProcessor.refreshTaskProcess(taskEntry, taskEvent, TaskConst.Trigger_Pool)) {
                SpringContext.getTaskService().doAfterTriggerTaskProgressChange(taskEntry, taskEvent.getPlayer());
            }
        }

        // 处理进行池任务
        List<TaskEntry> executingTaskList = taskInfo.getExecutingTaskByEventType(eventType);
        for (TaskEntry taskEntry : executingTaskList) {
            if (taskProcessor.refreshTaskProcess(taskEntry, taskEvent, TaskConst.Executing_Pool)) {
                SpringContext.getTaskService().doAfterExecutingTaskProgressChange(taskEntry, taskEvent.getPlayer());
            }
        }

        SpringContext.getTaskService().saveTaskInfo(player);
    }
}
