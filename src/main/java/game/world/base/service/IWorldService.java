package game.world.base.service;

import game.miniMap.model.Grid;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/2 下午4:38
 */

public interface IWorldService {
    /**
     * 服务器切图 这里账号,地图线程都会调用
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    void gatewayChangeMap(Player player, int mapId, boolean clientRequest);

    /**
     * 切图
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    // void changeMap(Player player, int mapId, boolean clientRequest);

    /**
     * 离开地图
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    void gatewayLeaveMap(Player player, int mapId, boolean clientRequest);

    /**
     * 离开地图
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    void leaveMap(Player player, int mapId, boolean clientRequest);

    /**
     * 打印地图信息
     *
     * @param player
     * @param mapId
     */
    void logMap(Player player, int mapId);

    /**
     * 地图移动 不要带开始坐标了 服务端做寻路
     *
     * @param player
     * @param targetPosition
     */
    void move(Player player, Grid targetPosition);

    // 内部调用方法区

    // /**
    // * 获取地图场景
    // * @param mapId
    // * @return
    // */
    // AbstractScene getScene(int mapId);
}
