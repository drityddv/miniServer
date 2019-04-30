package game.user.login.service;

import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import net.model.USession;

/**
 * 用户登陆
 *
 * @author : ddv
 * @since : 2019/4/30 上午10:56
 */

public interface ILoginService {

	/**
	 * 用户登录
	 *
	 * @param session
	 * @param request
	 */
	void login(USession session, CM_UserLogin request);

	/**
	 * 用户登出
	 *
	 * @param session
	 * @param request
	 */
	void logout(USession session, CM_UserLogout request);
}
