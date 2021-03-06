package game.world.instance.groupInstance.service;

import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.instance.base.model.BaseInstanceMapScene;

/**
 * 副本
 *
 * @author : ddv
 * @since : 2019/7/30 5:45 PM
 */

public interface IGroupInstanceService {

    /**
     * 初始化
     */
    void init();

    /**
     * 进入地图
     *
     * @param player
     * @param mapId
     * @param sceneId
     */
    void enterMap(Player player, int mapId, long sceneId);

    /**
     * 离开地图
     *
     * @param player
     */
    void leaveMap(Player player);

    /**
     * 移动
     *
     * @param player
     *
     * @param targetGrid
     */
    void doMove(Player player, Grid targetGrid);

    /**
     * 获取玩家当前所处场景
     *
     * @param player
     * @return
     */
    BaseInstanceMapScene getCurrentScene(Player player);

    /**
     * 打印地图
     *
     * @param player
     * @param mapId
     * @param sceneId
     */
    void doLogMap(Player player, int mapId, long sceneId);

    /**
     * 获取场景
     *
     * @param mapId
     * @param sceneId
     * @return
     */
    BaseInstanceMapScene getMapScene(int mapId, long sceneId);

    /**
     * 心跳
     *
     * @param mapId
     */
    void heartBeat(int mapId);

    /**
     * 销毁副本
     *
     * @param mapId
     * @param sceneId
     */
    void destroy(int mapId, long sceneId);

    /**
     * 关闭副本
     *
     * @param mapId
     * @param sceneId
     */
    void close(int mapId, long sceneId);

}
