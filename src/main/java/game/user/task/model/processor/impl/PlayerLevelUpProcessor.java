package game.user.task.model.processor.impl;

import org.springframework.stereotype.Component;

import game.user.task.constant.TaskEventType;
import game.user.task.event.TaskEvent;
import game.user.task.model.TaskCondition;
import game.user.task.model.processor.BaseTaskProcessor;

/**
 * @author : ddv
 * @since : 2019/8/2 9:18 PM
 */

@Component
public class PlayerLevelUpProcessor extends BaseTaskProcessor {
    @Override
    protected TaskEventType getType() {
        return TaskEventType.Player_LevelUp;
    }

    @Override
    protected int getValue(TaskEvent taskEvent, TaskCondition taskCondition) {
        return 1;
    }
}
