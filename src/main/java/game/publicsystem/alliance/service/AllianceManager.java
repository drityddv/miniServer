package game.publicsystem.alliance.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.publicsystem.alliance.entity.AllianceEnt;
import game.publicsystem.alliance.model.Alliance;

/**
 * @author : ddv
 * @since : 2019/8/4 1:26 AM
 */

@Component
public class AllianceManager {

    @Autowired
    private EntityCacheService<Long, AllianceEnt> entityCacheService;

    private Map<Long, Alliance> allianceMap = new ConcurrentHashMap<>();

    public AllianceEnt loadOrCreate(long serverId) {
        return entityCacheService.loadOrCreate(AllianceEnt.class, serverId, AllianceEnt::valueOf);
    }

    public void save(AllianceEnt ent) {
        entityCacheService.save(ent);
    }
}
