package game.user.task.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.item.base.model.AbstractItem;
import game.role.player.model.Player;
import game.user.task.constant.TaskConst;
import game.user.task.entity.TaskEnt;
import game.user.task.model.TaskEntry;
import game.user.task.model.TaskInfo;
import game.user.task.packet.SM_TaskInfoVo;
import game.user.task.resource.TaskResource;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * 任务接口
 *
 * @author : ddv
 * @since : 2019/8/2 3:37 PM
 */
@Component
public class TaskService implements ITaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskManager taskManager;

    @Override
    public TaskInfo getTaskInfo(Player player) {
        TaskEnt taskEnt = getTaskEnt(player);
        return taskEnt.getTaskInfo();
    }

    @Override
    public void doAfterTriggerTaskProgressChange(TaskEntry taskEntry, Player player) {
        if (canFinishTask(taskEntry, TaskConst.Trigger_Pool)) {
            doFinishTrigger(taskEntry, player);
        }
    }

    @Override
    public void doAfterExecutingTaskProgressChange(TaskEntry taskEntry, Player player) {
        TaskInfo taskInfo = player.getTaskInfo();
        if (canFinishTask(taskEntry, TaskConst.Executing_Pool)) {
            taskInfo.getFinishedTaskIdList().add(taskEntry.getTaskId());
            taskInfo.getExecutingTaskMap().remove(taskEntry.getTaskId());

            // 主线任务推进
            long nextTaskId = taskEntry.getTaskResource().getNextTaskId();
            if (nextTaskId != 0) {
                TaskEntry newTaskEntry = TaskEntry.valueOf(nextTaskId);
                taskInfo.putExecutingTask(newTaskEntry);
            }

            // 触发任务推进
            List<Long> triggerTaskIdList = taskEntry.getTaskResource().getTriggerTaskIdList();
            triggerTaskIdList.forEach(taskId -> {
                TaskEntry newTaskEntry = TaskEntry.valueOf(taskId);
                taskInfo.putTriggerTask(newTaskEntry);
            });

            // 发奖
            List<AbstractItem> items =
                SpringContext.getItemService().createItemsByDropConfig(taskEntry.getTaskResource().getRewardDropId());
            if (SpringContext.getPackService().isEnoughSize(player, items)) {
                SpringContext.getPackService().addItems(player, items);
            }
        }
    }

    @Override
    public void saveTaskInfo(Player player) {
        taskManager.save(getTaskEnt(player));
    }

    @Override
    public void loadTaskInfo(Player player) {
        TaskInfo taskInfo = getTaskInfo(player);
        if (!taskInfo.isLoaded()) {
            taskInfo.load();
        }
    }

    @Override
    public void acceptTask(Player player, long taskId) {
        TaskInfo taskInfo = player.getTaskInfo();
        TaskResource taskResource = taskManager.getTaskResource(taskId);
        if (taskResource == null) {
            logger.warn("玩家[{}] 接受任务[{}]失败 任务资源文件不存在!", player.getAccountId(), taskId);
            return;
        }

        if (taskInfo.getExecutingTaskMap().containsKey(taskId)) {
            logger.warn("玩家[{}] 已经接受任务[{}]!", player.getAccountId(), taskId);
            return;
        }

        // 触发池如果存在该任务 移到进行池
        if (taskInfo.getTriggerTaskMap().containsKey(taskId)) {
            TaskEntry taskEntry = taskInfo.getTriggerTask(taskId);
            taskInfo.removeTriggerTask(taskId);
            taskInfo.putExecutingTask(taskEntry);
            return;
        }

        // 都不存在 则new新的任务丢到进行池
        TaskEntry taskEntry = TaskEntry.valueOf(taskId);
        taskInfo.getExecutingTaskMap().put(taskId, taskEntry);
        saveTaskInfo(player);
    }

    @Override
    public void requestTaskInfo(Player player) {
        TaskInfo taskInfo = player.getTaskInfo();
        PacketUtil.send(player, SM_TaskInfoVo.valueOf(taskInfo));
    }

    @Override
    public void moveTriggerTask(Player player, long taskId) {
        TaskInfo taskInfo = player.getTaskInfo();
        if (!taskInfo.getTriggerTaskMap().containsKey(taskId)) {
            logger.warn("玩家[{}]提现触发池任务[{}]失败,触发池中没有此任务!", player.getAccountId(), taskId);
            return;
        }

        taskInfo.moveTriggerToExecuting(taskId);
        saveTaskInfo(player);
    }

    // 完成触发任务相关流程 不作任何处理 触发池取出只能等待其他任务激活
    private void doFinishTrigger(TaskEntry taskEntry, Player player) {
        // 从触发池取出 放进待完成池
    }

    // 检查任务是否可以完成 只检查!!!
    private boolean canFinishTask(TaskEntry taskEntry, int poolType) {
        List<Integer> finishProcessor = taskEntry.getTaskResource().getFinishProcessor();
        int index = 0;

        for (Integer totalProcessor : finishProcessor) {
            Integer realProgress = null;

            if (poolType == TaskConst.Trigger_Pool) {
                realProgress = taskEntry.getTriggerProgress().get(index);
            }

            if (poolType == TaskConst.Executing_Pool) {
                realProgress = taskEntry.getExecutingProgress().get(index);
            }

            if (realProgress == null || realProgress < totalProcessor) {
                return false;
            }

            index++;
        }
        return true;
    }

    private TaskEnt getTaskEnt(Player player) {
        return taskManager.loadOrCreate(player.getAccountId());
    }
}
