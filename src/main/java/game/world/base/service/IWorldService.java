package game.world.base.service;

import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/2 下午4:38
 */

public interface IWorldService {
    /**
     * 切图
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    void changeMap(Player player, int mapId, boolean clientRequest);

    /**
     * 服务器切图 这里账号,地图线程都会调用
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    void gatewayChangeMap(Player player, int mapId, boolean clientRequest);

    /**
     * 离开地图
     *
     * @param player
     * @param mapId
     * @param clientRequest
     */
    void gatewayLeaveMap(Player player, int mapId, boolean clientRequest);

    /**
     * 打印地图信息
     *
     * @param player
     * @param mapId
     */
    void logMap(Player player, int mapId);
}
