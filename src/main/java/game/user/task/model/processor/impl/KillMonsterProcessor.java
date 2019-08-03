package game.user.task.model.processor.impl;

import org.springframework.stereotype.Component;

import game.user.task.constant.TaskEventType;
import game.user.task.event.TaskEvent;
import game.user.task.model.TaskCondition;
import game.user.task.model.TaskEventParam;
import game.user.task.model.processor.BaseTaskProcessor;

/**
 * @author : ddv
 * @since : 2019/8/2 2:20 PM
 */
@Component
public class KillMonsterProcessor extends BaseTaskProcessor {

    @Override
    protected TaskEventType getType() {
        return TaskEventType.Kill_Monster_NormalMap;
    }

    @Override
    protected int getValue(TaskEvent taskEvent, TaskCondition taskCondition) {
        TaskEventParam eventParam = taskEvent.getEventParam();
        long monsterId = taskCondition.getParam(0);
        if (eventParam.getCreatureResource().getConfigId() != monsterId) {
            return 0;
        }
        return eventParam.getValue();
    }

}
