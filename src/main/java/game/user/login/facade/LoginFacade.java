package game.user.login.facade;

import game.user.login.packet.CM_UserLogin;
import middleware.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : ddv
 * @since : 2019/4/28 下午8:25
 */
@Component
public class LoginFacade {

	private static final Logger logger = LoggerFactory.getLogger(LoginFacade.class);

	@HandlerAnno
	public void userLogin(CM_UserLogin request) {
		logger.info("user login method invoked");
	}

}
