package game.user.mapinfo.service;

import game.role.player.model.Player;
import game.user.mapinfo.entity.MapInfoEnt;

/**
 * 玩家地图相关信息
 *
 * @author : ddv
 * @since : 2019/7/2 下午5:10
 */

public interface IMapInfoService {

    /**
     * 获取玩家地图信息
     *
     * @param player
     * @return
     */
    MapInfoEnt getMapInfoEnt(Player player);

    /**
     * 保存
     *
     * @param player
     * @param ent
     */
    void saveMapInfoEnt(Player player, MapInfoEnt ent);
}
