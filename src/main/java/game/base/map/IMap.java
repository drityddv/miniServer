package game.base.map;

import game.base.map.base.MapCreature;

/**
 * @author : ddv
 * @since : 2019/5/6 下午6:00
 */

public interface IMap {

	/**
	 * 目标物体移动
	 *
	 * @param objectId
	 * @param x
	 * @param y
	 */
	void move(long objectId, int x, int y);

	/**
	 * 打印地图信息
	 */
	void printMap();

	/**
	 * 获取mapId
	 *
	 * @return
	 */
	long getCurrentMapId();

	/**
	 * 玩家是否能进入地图
	 *
	 * @param accountId
	 * @return
	 */
	boolean canEnterMap(String accountId);

	/**
	 * 获取地图中的单位
	 *
	 * @param objectId
	 * @return
	 */
	MapCreature getCreature(long objectId);

	/**
	 * 加入单位到地图
	 */
	void addCreature(MapCreature creature);
}
