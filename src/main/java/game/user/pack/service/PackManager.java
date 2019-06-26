package game.user.pack.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.user.item.resource.ItemResource;
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
    private Map<Long, ItemResource> itemStorage;

    public PackEnt loadOrCreate(Long playerId) {
        return entEntityCacheService.loadOrCreate(PackEnt.class, playerId, PackEnt::valueOf);
    }

    public void save(Long PlayerId) {
        entEntityCacheService.save(loadOrCreate(PlayerId));
    }

    public ItemResource getResource(Long configId) {
        return itemStorage.get(configId);
    }
}
