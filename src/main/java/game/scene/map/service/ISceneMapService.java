package game.scene.map.service;

import game.base.map.IMap;
import game.user.player.model.Player;

/**
 * 场景地图service
 *
 * @author : ddv
 * @since : 2019/5/7 上午10:33
 */

public interface ISceneMapService {

    /**
     * 进入指定地图
     *
     * @param player
     * @param mapId
     */
    void enterMap(Player player, long mapId);

    /**
     * 离开地图
     *
     * @param player
     * @param mapId
     */
    void leaveMap(Player player, long mapId);

    /**
     * 切换地图
     *
     * @param player
     * @param fromMapId
     * @param targetMapId
     */
    void changeMap(Player player, long fromMapId, long targetMapId);

    /**
     * 打印对应map的信息
     *
     * @param mapId
     */
    void logBasicMapInfo(long mapId);

    /**
     * 地图特殊传送机制
     *
     * @param player
     * @param mapId
     */
    void transfer(Player player, long mapId);

    /**
     * 地图中单位移动
     *
     * @param player
     * @param mapId
     * @param targetX
     * @param targetY
     */
    void move(Player player, long mapId, int targetX, int targetY);

    /**
     * 获取地图
     *
     * @param mapId
     * @return
     */
    IMap getMapResource(long mapId);

	/**
	 * 修改
	 * @param playerId
	 * @param mapId
	 */
	void modifyPlayerMapStatus(long playerId,long mapId);
}
