package game.map.handler;

import java.util.Collections;
import java.util.Map;

import game.map.base.AbstractMovableScene;
import game.map.constant.MapGroupType;
import game.map.visible.AbstractMapObject;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/2 下午9:55
 */

public interface IMapHandler {

    /**
     * 获得地图类型
     *
     * @return
     */
    MapGroupType getGroupType();

    /**
     * 离开地图
     *
     * @param player
     */
    void leaveMap(Player player);

    /**
     * 真正进入地图
     *
     * @param player
     * @param mapId
     * @param sceneId
     */
    void realEnterMap(Player player, int mapId, long sceneId);

    /**
     * 地图心跳
     *
     * @param mapId
     */
    void heartBeat(int mapId);

    /**
     * 获取玩家单位
     *
     * @param mapId
     * @param sceneId
     * @return
     */
    default Map<Long, PlayerMapObject> getPlayerObjects(int mapId, long sceneId) {
        return Collections.emptyMap();
    }

    /**
     * 获取怪物单位
     *
     * @param mapId
     * @param sceneId
     * @return
     */
    default Map<Long, MonsterMapObject> getMonsterObjects(int mapId, long sceneId) {
        return Collections.emptyMap();
    }

    /**
     * 获取单位
     *
     * @param mapId
     * @param unitId
     * @return
     */
    default AbstractMapObject getUnit(int mapId, long sceneId, long unitId) {
        AbstractMapObject unit = getPlayerObjects(mapId, sceneId).get(unitId);
        if (unit == null) {
            unit = getMonsterObjects(mapId, sceneId).get(unitId);
        }
        return unit;
    }

    /**
     * 获得场景
     *
     * @param mapId
     * @param sceneId
     * @return
     */
    AbstractMovableScene getMapScene(int mapId, long sceneId);

    /**
     * 测试用
     *
     * @param mapId
     * @param sceneId
     * @param param
     */
    void test(int mapId, long sceneId, Map<String, Object> param);

    /**
     * show
     *
     * @param player
     */
    void showAround(Player player);
}
