package game.publicsystem.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.publicsystem.alliance.constant.AllianceConst;
import game.publicsystem.rank.entity.ServerRankEnt;

/**
 * @author : ddv
 * @since : 2019/8/5 4:48 PM
 */

@Component
public class RankManager {

    @Autowired
    private EntityCacheService<Long, ServerRankEnt> entityCacheService;

    public ServerRankEnt loadOrCreate() {
        return entityCacheService.loadOrCreate(ServerRankEnt.class, AllianceConst.SERVER_ID, ServerRankEnt::valueOf);
    }

    public void save(ServerRankEnt ent) {
        entityCacheService.save(ent);
    }
}
