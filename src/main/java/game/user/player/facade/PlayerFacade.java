package game.user.player.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.user.login.event.PlayerLoginBeforeEvent;
import game.user.player.event.PlayerLevelUpEvent;
import game.user.player.service.PlayerService;
import middleware.anno.EventReceiver;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:11
 */
@Component
public class PlayerFacade {

    private static Logger logger = LoggerFactory.getLogger(PlayerFacade.class);

    @Autowired
    private PlayerService playerService;

    @EventReceiver
    public void playerLoginBefore(PlayerLoginBeforeEvent event) {
        try {
            playerService.loadPlayerAttribute(event.getPlayer());
        } catch (Exception e) {
            logger.info("玩家登陆前置事件出错,[{}]", e.getClass());
            e.printStackTrace();
        }

    }

    @EventReceiver
    public void playerLevelUp(PlayerLevelUpEvent event) {
        try {
            playerService.playerLevelUp(event.getPlayer());
        } catch (Exception e) {
            logger.info("玩家升级事件出错,[{}]", e.getClass());
            e.printStackTrace();
        }
    }


}
