package game.user.task.event;

import ebus.model.IEvent;
import game.role.player.model.Player;
import game.user.task.constant.TaskEventType;
import game.user.task.model.TaskEventParam;

/**
 * @author : ddv
 * @since : 2019/8/2 12:06 PM
 */

public class TaskEvent implements IEvent {
    private Player player;
    private TaskEventType taskEventType;
    private TaskEventParam eventParam;

    public static TaskEvent valueOf(Player player, TaskEventType taskEventType) {
        TaskEvent event = new TaskEvent();
        event.player = player;
        event.taskEventType = taskEventType;
        return event;
    }

    public static TaskEvent valueOf(Player player, TaskEventType taskEventType, TaskEventParam eventParam) {
        TaskEvent event = new TaskEvent();
        event.player = player;
        event.taskEventType = taskEventType;
        event.eventParam = eventParam;
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public TaskEventType getTaskEventType() {
        return taskEventType;
    }

    public TaskEventParam getEventParam() {
        return eventParam;
    }
}
