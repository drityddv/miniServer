package game.map.handler;

import game.map.base.AbstractPlayerMapInfo;
import game.map.constant.MapGroupType;
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

}
