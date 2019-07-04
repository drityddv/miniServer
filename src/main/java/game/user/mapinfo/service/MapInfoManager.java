package game.user.mapinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.user.mapinfo.entity.MapInfoEnt;

/**
 * @author : ddv
 * @since : 2019/7/2 下午6:12
 */
@Component
public class MapInfoManager {

    @Autowired
    private EntityCacheService<String, MapInfoEnt> cacheService;

    public MapInfoEnt loadOrCreate(String accountId) {
        return cacheService.loadOrCreate(MapInfoEnt.class, accountId, MapInfoEnt::valueOf);
    }

    public void save(MapInfoEnt ent) {
        cacheService.save(ent);
    }
}
