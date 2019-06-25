package game.user.player.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.user.login.event.PlayerLoginBeforeEvent;
import game.user.player.service.PlayerService;
import middleware.anno.EventReceiver;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:11
 */
@Component
public class PlayerFacade {

    @Autowired
    private PlayerService playerService;

    @EventReceiver
    public void playerLoginBefore(PlayerLoginBeforeEvent event) {
        playerService.loadPlayerAttribute(event.getPlayer());
    }
}
