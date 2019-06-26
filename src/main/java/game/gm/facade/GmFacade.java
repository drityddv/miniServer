package game.gm.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.common.I18N;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.gm.packet.CM_GmCommand;
import middleware.anno.HandlerAnno;
import net.model.USession;
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
     * @param session
     * @param request
     */
    @HandlerAnno
    public void invoke(USession session, CM_GmCommand request) {
        try {
            SpringContext.getGmService().invoke(session, request);
            PacketUtil.send(session, SM_Message.valueOf(I18N.OPERATION_SUCCESS));
        } catch (RequestException e) {
            PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(session, SM_Message.valueOf(I18N.SERVER_ERROR));
            logger.error(e.toString());
        }
    }

}
