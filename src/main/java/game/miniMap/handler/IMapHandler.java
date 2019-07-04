package game.miniMap.handler;

import game.user.mapinfo.entity.MapInfoEnt;
import game.user.player.model.Player;
import game.world.AbstractMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/2 下午9:55
 */

public interface IMapHandler {

    /**
     * 获取玩家地图信息
     *
     * @param player
     * @param ent
     * @param <T>
     * @return
     */
    <T extends AbstractMapInfo> T getMapInfo(Player player, MapInfoEnt ent);

    /**
     * 获得地图类型
     *
     * @return
     */
    MapGroupType getGroupType();

    /**
     * 进入地图 不做检查 调用前后自行检查canEnterMap()
     *
     * @param player
     * @param mapId
     */
    void doEnterMap(Player player, int mapId);

    /**
     * 离开地图
     *
     * @param player
     * @param newMapId
     */
    void leaveMap(Player player, int newMapId);

    /**
     * 玩家下线之后 地图处理
     *
     * @param player
     */
    void logout(Player player);

    /**
     * 真正进入地图
     *
     * @param player
     */
    void realEnterMap(Player player);

}
