package game.world.neutralMap.service;

import game.miniMap.model.Grid;
import game.user.player.model.Player;
import game.world.neutralMap.model.NeutralMapScene;

/**
 * 中立地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午5:29
 */

public interface INeutralMapService {
    /**
     * 初始化
     */
    void init();

    /**
     * 是否可以进入地图
     *
     * @param player
     * @param mapId
     * @return
     */
    boolean canEnterMap(Player player, int mapId);

    /**
     * 是否可以进入地图
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    void canEnterMapThrow(Player player, int mapId, boolean clientRequest);

    /**
     * 进入地图
     *
     * @param player
     * @param mapId
     */
    void enterMap(Player player, int mapId);

    /**
     * 离开地图 不进入新的地图
     *
     * @param player
     */
    void leaveMap(Player player);

    /**
     * 离开地图 并且进入新的地图
     *
     * @param player
     * @param newMapId
     */
    void leaveMap(Player player, int newMapId);

    /**
     * 移动
     *
     * @param player
     * @param mapId
     * @param currentGrid
     * @param targetGrid
     */
    void doMove(Player player, int mapId, Grid currentGrid, Grid targetGrid);

    /**
     * 获取可以进入的场景
     *
     * @param accountId
     * @param mapId
     * @return
     */
    NeutralMapScene getEnterScene(String accountId, int mapId);

    /**
     * 获取玩家当前所处场景
     *
     * @param accountId
     * @param mapId
     * @return
     */
    NeutralMapScene getCurrentScene(String accountId, int mapId);

    /**
     * 打印地图
     *
     * @param player
     * @param mapId
     */
    void logMap(Player player, int mapId);
}
