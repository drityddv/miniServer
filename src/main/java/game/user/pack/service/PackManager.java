package game.user.pack.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.center.item.resource.Item;
import game.user.pack.entity.PackEnt;
import middleware.anno.Static;

/**
 * @author : ddv
 * @since : 2019/6/1 上午10:09
 */

@Component
public class PackManager {

    @Autowired
    private EntityCacheService<Long, PackEnt> entEntityCacheService;

    @Static
    private Map<Long, Item> itemStorage;

    public PackEnt loadOrCreate(Long playerId) {
        return entEntityCacheService.loadOrCreate(PackEnt.class, playerId, PackEnt::valueOf);
    }

}
