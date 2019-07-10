package game.role.player.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ebus.anno.EventReceiver;
import game.dispatch.anno.HandlerAnno;
import game.gm.event.HotFixEvent;
import game.role.player.packet.CM_CreatePlayer;
import game.role.player.service.PlayerService;
import game.user.login.event.PlayerLoadSynEvent;
import net.model.USession;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:11
 */
@Component
public class PlayerFacade {

    private static Logger logger = LoggerFactory.getLogger(PlayerFacade.class);

    @Autowired
    private PlayerService playerService;

    /**
     * 创建角色
     *
     * @param session
     * @param request
     */
    @HandlerAnno
    public void createPlayer(USession session, CM_CreatePlayer request) {
        try {
            playerService.createPlayer(session, request.getSex());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @EventReceiver
    public void playerLoginBefore(PlayerLoadSynEvent event) {
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
