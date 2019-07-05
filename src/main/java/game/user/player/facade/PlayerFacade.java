package game.user.player.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.gm.event.HotFixEvent;
import game.user.login.event.PlayerLoginBeforeEvent;
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

    /**
     * 热更资源事件
     *
     * @param event
     */
    @EventReceiver
    public void hotFixPlayerAttribute(HotFixEvent event) {
        try {
            playerService.hotFixCorrect(event.getPlayer(), event.getResourceName());
        } catch (Exception e) {
            logger.info("玩家hotfix事件出错,[{}]", e.getClass());
            e.printStackTrace();
        }
    }

}
