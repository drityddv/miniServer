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
     * @param targetX
     * @param targetY
     */
    void move(long objectId, int targetX, int targetY);

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
     *
     * @param creature
     * @return
     */
    void addCreature(MapCreature creature);

    /**
     * 删除地图中对应单位
     *
     * @param objectId
     */
    void deleteCreature(long objectId);

    /**
     * 地图特殊机制 传送到下一张地图
     *
     * @param objectId
     */
    void transfer(long objectId);

    /**
     * 计算与目标点之间的距离
     *
     * @param objectId
     * @param targetObjectId
     * @return
     */
    double calculateDistance(long objectId, long targetObjectId);

}
