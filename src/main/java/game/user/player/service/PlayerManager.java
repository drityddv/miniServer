package game.user.player.service;

import org.springframework.stereotype.Component;

import db.middleware.HibernateUtil;
import db.middleware.IOrmTemplate;
import game.user.player.entity.PlayerEnt;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:05
 */
@Component
public class PlayerManager {

    private IOrmTemplate<String, PlayerEnt> ormTemplate = new HibernateUtil<>();

    public PlayerEnt loadOrCreate(String accountId) {
        return ormTemplate.loadOrCreate(PlayerEnt.class, accountId, PlayerEnt::valueOf);
    }

    public void saveEntity(PlayerEnt playerEnt) {
        ormTemplate.save(playerEnt);
    }
}
