package game.scene.map.service;

import game.base.map.IMap;

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
     * @param accountId
     * @param mapId
     */
    void enterMap(String accountId, long mapId);

    /**
     * 离开地图
     *
     * @param accountId
     * @param mapId
     */
    void leaveMap(String accountId, long mapId);

    /**
     * 切换地图
     *
     * @param accountId
     * @param fromMapId
     * @param targetMapId
     */
    void changeMap(String accountId, long fromMapId, long targetMapId);

    /**
     * 打印对应map的信息
     *
     * @param mapId
     */
    void logBasicMapInfo(long mapId);

    /**
     * 地图特殊传送机制
     *
     * @param accountId
     * @param mapId
     */
    void transfer(String accountId, long mapId);

    /**
     * 地图中单位移动
     *
     * @param accountId
     * @param mapId
     * @param targetX
     * @param targetY
     */
    void move(String accountId, long mapId, int targetX, int targetY);

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
