package game.user.pack.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.common.I18N;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.user.pack.packet.CM_PackInfo;
import game.user.player.model.Player;
import middleware.anno.HandlerAnno;
import net.model.USession;
import net.utils.PacketUtil;
import spring.SpringContext;
import utils.SimpleUtil;

/**
 * @author : ddv
 * @since : 2019/6/10 下午6:43
 */
@Component
public class PackFacade {

    private static final Logger logger = LoggerFactory.getLogger(PackFacade.class);

    /**
     * 玩家获取背包详情
     *
     * @param session
     * @param request
     */
    @HandlerAnno
    public void getPlayerPack(USession session, CM_PackInfo request) {
        try {
            Player player = SimpleUtil.getPlayerFromSession(session);
            SpringContext.getPackService().getPlayerPack(player);
        } catch (RequestException e) {
            PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(session, SM_Message.valueOf(I18N.SERVER_ERROR));
            e.printStackTrace();
        }
    }
}
