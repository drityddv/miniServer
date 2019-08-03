package game.user.task.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.user.task.constant.TaskEventType;
import game.user.task.constant.TaskType;
import game.user.task.model.TaskCondition;
import resource.anno.Init;
import resource.anno.MiniResource;
import resource.constant.CsvSymbol;
import utils.StringUtil;

/**
 * @author : ddv
 * @since : 2019/8/1 11:52 PM
 */
@MiniResource
public class TaskResource {
    private long taskId;
    // 主线,日常,周常等...
    private TaskType taskType;
    private Set<TaskEventType> taskEventTypes;
    private int taskTypeId;
    // 奖励配置
    private long rewardDropId;
    // 前置主线任务id
    private long preTaskId;
    // 下一个主线任务id
    private long nextTaskId;
    // 完成触发的下个任务id
    private List<Long> triggerTaskIdList;
    private String triggerTaskIdString;
    // 完成条件
    private List<TaskCondition> finishCondList;
    // 1:1&1,2&2;4:3
    private String finishCondString;

    @Init
    private void init() {
        taskType = TaskType.getById(taskTypeId);
        analyseTriggerId();
        analyseCondition();
        analyseEventType();

    }

    private void analyseEventType() {
        taskEventTypes = new HashSet<>();
        finishCondList.forEach(taskCondition -> {
            taskEventTypes.add(taskCondition.getType());
        });
    }

    private void analyseTriggerId() {
        triggerTaskIdList = new ArrayList<>();
        if (StringUtil.isNotEmpty(triggerTaskIdString)) {
            for (String s : triggerTaskIdString.split(CsvSymbol.Comma)) {
                triggerTaskIdList.add(Long.parseLong(s));
            }
        }
    }

    private void analyseCondition() {
        finishCondList = new ArrayList<>();
        // 1:1&1,2&2;4:3
        String[] split = finishCondString.split(CsvSymbol.Semicolon);
        for (String s : split) {
            // 1 -- 1&1;2&2
            String[] split1 = s.split(CsvSymbol.Colon);
            int id = Integer.parseInt(split1[0]);
            TaskEventType eventTypeEnum = TaskEventType.getById(id);
            finishCondList.addAll(eventTypeEnum.parseParam(split1[1]));
        }
        finishCondList.forEach(taskCondition -> taskCondition.loadProgress());
    }

    public long getTaskId() {
        return taskId;
    }

    public long getRewardDropId() {
        return rewardDropId;
    }

    public List<TaskCondition> getFinishCondList() {
        return finishCondList;
    }

    public List<Long> getTriggerTaskIdList() {
        return triggerTaskIdList;
    }

    public List<Integer> getFinishProcessor() {
        List<Integer> result = new ArrayList<>();
        finishCondList.forEach(taskCondition -> {
            result.add(taskCondition.getTotalProgress());
        });
        return result;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public long getPreTaskId() {
        return preTaskId;
    }

    public long getNextTaskId() {
        return nextTaskId;
    }

    public Set<TaskEventType> getTaskEventTypeSet() {
        return taskEventTypes;

    }
}
