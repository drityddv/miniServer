package game.user.player.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.IEntityCacheService;
import game.user.player.entity.PlayerEnt;
import game.user.player.resource.PlayerResource;
import middleware.anno.Static;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:05
 */
@Component
public class PlayerManager {

    @Autowired
    private IEntityCacheService<String, PlayerEnt> entEntityCache;

    @Static
    private Map<Integer, PlayerResource> resourceMap;

    public PlayerEnt loadOrCreate(String accountId) {
        return entEntityCache.loadOrCreate(PlayerEnt.class, accountId, PlayerEnt::valueOf);
    }

    public void saveEntity(PlayerEnt playerEnt) {
        entEntityCache.save(playerEnt);
    }

    public PlayerResource getPlayerResource(int level) {
        return resourceMap.get(level);
    }

}
