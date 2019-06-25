package game.user.login.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.common.Ii8n;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.CM_UserRegister;
import middleware.anno.HandlerAnno;
import net.model.USession;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/4/28 下午8:25
 */
@Component
public class LoginFacade {

    private static final Logger logger = LoggerFactory.getLogger(LoginFacade.class);

    /**
     * 用户登录
     *
     * @param session
     * @param request
     */
    @HandlerAnno
    public void userLogin(USession session, CM_UserLogin request) {
        try {
            SpringContext.getLoginService().login(session, request);
        } catch (RequestException e) {
            PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
            e.printStackTrace();
        }
    }

    @HandlerAnno
    public void userRegister(USession session, CM_UserRegister request) {
        try {
            SpringContext.getLoginService().register(session, request);
        } catch (RequestException e) {
            PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
            e.printStackTrace();
        }
    }

    /**
     * 用户登出
     *
     * @param session
     * @param request
     */
    @HandlerAnno
    public void userLogout(USession session, CM_UserLogout request) {
        try {
            SpringContext.getLoginService().logout(session, request);
        } catch (RequestException e) {
            PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
            e.printStackTrace();
        }
    }

}
