package game.user.task.packet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.user.task.model.TaskEntry;
import game.user.task.model.TaskInfo;

/**
 * @author : ddv
 * @since : 2019/8/2 10:17 PM
 */

public class SM_TaskInfoVo {
    private static final Logger logger = LoggerFactory.getLogger(SM_TaskInfoVo.class);

    // 完成的任务id集合
    private Set<Long> finishedTaskIdList = new HashSet<>();
    // 正在进行中的任务
    private Map<Long, TaskEntry> executingTaskMap = new HashMap<>();
    // 触发流程中的任务
    private Map<Long, TaskEntry> triggerTaskMap = new HashMap<>();

    public static SM_TaskInfoVo valueOf(TaskInfo taskInfo) {
        SM_TaskInfoVo vo = new SM_TaskInfoVo();

        taskInfo.getFinishedTaskIdList().forEach(taskId -> {
            vo.finishedTaskIdList.add(Long.valueOf(taskId));
        });

        taskInfo.getExecutingTaskMap().values().forEach(taskEntry -> {
            vo.executingTaskMap.put(taskEntry.getTaskId(), taskEntry);
        });

        taskInfo.getTriggerTaskMap().values().forEach(taskEntry -> {
            vo.triggerTaskMap.put(taskEntry.getTaskId(), taskEntry);
        });
        return vo;
    }

    @Action
    private void action() {
        logger.info("完成的任务集合[{}]", finishedTaskIdList);
        logger.info("进行池任务集合");
        executingTaskMap.values().forEach(taskEntry -> {
            taskEntry.log(logger);
        });

        logger.info("触发池任务集合");
        triggerTaskMap.values().forEach(taskEntry -> {
            taskEntry.log(logger);
        });
    }
}
