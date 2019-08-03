package game.user.task.service;

import game.role.player.model.Player;
import game.user.task.model.TaskEntry;
import game.user.task.model.TaskInfo;

/**
 * 任务接口
 *
 * @author : ddv
 * @since : 2019/8/2 11:44 AM
 */

public interface ITaskService {

    /**
     * 获取玩家任务数据
     *
     * @param player
     * @return
     */
    TaskInfo getTaskInfo(Player player);

    /**
     * 触发池任务进度变化处理
     *
     * @param taskEntry
     * @param player
     */
    void doAfterTriggerTaskProgressChange(TaskEntry taskEntry, Player player);

    /**
     * 进行池任务进度变化处理
     *
     * @param taskEntry
     * @param player
     */
    void doAfterExecutingTaskProgressChange(TaskEntry taskEntry, Player player);

    /**
     * 保存玩家任务数据
     *
     * @param player
     */
    void saveTaskInfo(Player player);

    /**
     * 加载玩家任务数据
     *
     * @param player
     */
    void loadTaskInfo(Player player);

    /**
     * 接受任务
     *
     * @param player
     * @param taskId
     */
    void acceptTask(Player player, long taskId);

    /**
     * 查询玩家任务数据
     *
     * @param player
     */
    void requestTaskInfo(Player player);
}
