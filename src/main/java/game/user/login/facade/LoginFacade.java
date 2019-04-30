package game.user.login.facade;

import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import middleware.anno.HandlerAnno;
import net.model.USession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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
		logger.info("user login method invoked");
		try {
			SpringContext.getLoginService().login(session, request);
		} catch (Exception e) {
			logger.error(e.toString());
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
		logger.info("user logout method invoked");
	}

}
