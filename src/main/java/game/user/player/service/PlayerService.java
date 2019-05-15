package game.user.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:05
 */
@Component
public class PlayerService implements IPlayerService {

    @Autowired
    private PlayerManager playerManager;

    @Override
    public Player getPlayerByAccountId(String accountId) {
        return playerManager.loadOrCreate(accountId).getPlayer();
    }
}
