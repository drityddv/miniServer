package game.gm.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.gm.packet.CM_GmCommand;
import game.role.player.model.Player;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/5/7 下午2:48
 */
@Component
public class GmFacade {

    private static final Logger logger = LoggerFactory.getLogger(GmFacade.class);

    /**
     * 用户登录
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void invoke(Player player, CM_GmCommand request) {
        try {
            SpringContext.getGmService().invoke(player, request);
            PacketUtil.send(player, SM_Message.valueOf(I18N.OPERATION_SUCCESS));
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(player, SM_Message.valueOf(I18N.SERVER_ERROR));
            logger.error(e.toString());
        }
    }

}
