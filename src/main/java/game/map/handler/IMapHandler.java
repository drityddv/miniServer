package game.map.handler;

import java.util.Collections;
import java.util.Map;

import game.base.effect.model.buff.BaseCreatureBuff;
import game.map.base.AbstractMovableScene;
import game.map.base.AbstractPlayerMapInfo;
import game.map.constant.MapGroupType;
import game.map.visible.AbstractVisibleMapObject;
import game.map.visible.PlayerVisibleMapObject;
import game.map.visible.impl.MonsterVisibleMapObject;
import game.role.player.model.Player;
import game.user.mapinfo.entity.MapInfoEnt;

/**
 * @author : ddv
 * @since : 2019/7/2 下午9:55
 */

public interface IMapHandler {

    /**
     * 获取玩家的地图记录信息
     *
     * @param player
     * @param ent
     * @param <T>
     * @return
     */
    <T extends AbstractPlayerMapInfo> T getMapInfo(Player player, MapInfoEnt ent);

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
     */
    void realEnterMap(Player player, int mapId);

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
     * @return
     */
    default Map<Long, PlayerVisibleMapObject> getPlayerObjects(int mapId) {
        return Collections.emptyMap();
    }

    /**
     * 获取怪物单位
     *
     * @param mapId
     * @return
     */
    default Map<Long, MonsterVisibleMapObject> getMonsterObjects(int mapId) {
        return Collections.emptyMap();
    }

    /**
     * 获取单位
     *
     * @param mapId
     * @param unitId
     * @return
     */
    default AbstractVisibleMapObject getUnit(int mapId, long unitId) {
        AbstractVisibleMapObject unit = getPlayerObjects(mapId).get(unitId);
        if (unit == null) {
            unit = getMonsterObjects(mapId).get(unitId);
        }
        return unit;
    }

    /**
     * 获得地图内注册的buff[周期与一次性的都会在这里注册]
     *
     * @param mapId
     * @return
     */
    default Map<Long, BaseCreatureBuff> getBuffEffects(int mapId) {
        return Collections.emptyMap();
    }

    /**
     * 获得场景
     *
     * @param mapId
     * @return
     */
    AbstractMovableScene getMapScene(int mapId);

    /**
     * 测试用
     *
     * @param mapId
     * @param param
     */
    void test(int mapId, Map<String, Object> param);

}
