package game.scene.map.service;

import game.scene.map.packet.CM_ChangeMap;
import game.scene.map.packet.CM_EnterMap;
import game.scene.map.packet.CM_LeaveMap;

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
	 * @param request
	 */
	void enterMap(String accountId, CM_EnterMap request);

	/**
	 * 离开地图
	 *
	 * @param accountId
	 * @param request
	 */
	void leaveMap(String accountId, CM_LeaveMap request);

	/**
	 * 切换地图
	 *
	 * @param accountId
	 * @param request
	 */
	void changeMap(String accountId, CM_ChangeMap request);


	// gm支持

	/**
	 *	打印对应map的信息
	 * @param mapId
	 */
	void logBasicMapInfo(long mapId);
}
