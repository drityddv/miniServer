package game.user.login.service;

import net.model.USession;
import game.user.login.entity.UserEnt;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.CM_UserRegister;

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

	/**
	 * 用户注册
	 *
	 * @param session
	 * @param request
	 */
	void register(USession session, CM_UserRegister request);

	/**
	 * 获取用户实体信息 这个类不允许做修改
	 * @param session
	 * @return
	 */
	UserEnt getUserEnt(USession session);
}
