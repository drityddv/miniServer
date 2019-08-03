package game.user.task.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ebus.anno.EventReceiver;
import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.role.player.event.PlayerLevelUpEvent;
import game.role.player.model.Player;
import game.user.login.event.PlayerLoadSynEvent;
import game.user.task.constant.TaskEventType;
import game.user.task.event.TaskEvent;
import game.user.task.model.TaskEventParam;
import game.user.task.model.processor.TaskEventHandler;
import game.user.task.packet.CM_AcceptTask;
import game.user.task.packet.CM_TaskInfo;
import game.user.task.service.ITaskService;
import game.world.fight.event.KillMonsterEvent;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * 任务gateway
 *
 * @author : ddv
 * @since : 2019/8/2 12:17 PM
 */
@Component
public class TaskFacade {

    @Autowired
    private TaskEventHandler taskEventHandler;

    @Autowired
    private ITaskService taskService;

    /**
     * 请求玩家数据
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void requestTaskInfo(Player player, CM_TaskInfo request) {
        try {
            taskService.requestTaskInfo(player);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接受任务
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void acceptTask(Player player, CM_AcceptTask request) {
        try {
            taskService.acceptTask(player, request.getTaskId());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理任务事件
     *
     * @param taskEvent
     */
    @EventReceiver
    public void handlerTaskEvent(TaskEvent taskEvent) {
        taskEventHandler.handler(taskEvent);
    }

    /**
     * 杀怪事件
     *
     * @param event
     */
    @EventReceiver
    public void killMonsterEvent(KillMonsterEvent event) {
        TaskEventParam taskEventParam = new TaskEventParam();
        taskEventParam.setPlayer(event.getPlayer());
        taskEventParam.setMapId(event.getMapId());
        taskEventParam.setValue(1);
        taskEventParam.setCreatureResource(event.getCreatureResource());
        SpringContext.getEventBus()
            .pushEventSyn(TaskEvent.valueOf(event.getPlayer(), TaskEventType.Kill_Monster_NormalMap, taskEventParam));
    }

    @EventReceiver
    public void playerLevelUpEvent(PlayerLevelUpEvent event) {
        TaskEventParam taskEventParam = new TaskEventParam();
        taskEventParam.setPlayer(event.getPlayer());
        SpringContext.getEventBus()
            .pushEventSyn(TaskEvent.valueOf(event.getPlayer(), TaskEventType.Player_LevelUp, taskEventParam));
    }

    /**
     * 加载任务数据
     *
     * @param event
     */
    @EventReceiver
    public void loadTaskInfo(PlayerLoadSynEvent event) {
        taskService.loadTaskInfo(event.getPlayer());
    }
}
