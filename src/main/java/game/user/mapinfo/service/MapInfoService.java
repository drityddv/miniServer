package game.user.mapinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.user.mapinfo.entity.MapInfoEnt;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/2 下午6:11
 */
@Component
public class MapInfoService implements IMapInfoService {

    @Autowired
    private MapInfoManager mapInfoManager;

    @Override
    public MapInfoEnt getMapInfoEnt(Player player) {
        return mapInfoManager.loadOrCreate(player.getAccountId());
    }

    @Override
    public void saveMapInfoEnt(Player player, MapInfoEnt ent) {
        mapInfoManager.save(ent);
    }
}
