package game.user.login.service;

import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.SM_LoginSuccess;
import io.netty.channel.Channel;
import middleware.manager.SessionManager;
import net.model.PacketProtocol;
import net.model.USession;
import net.utils.PacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : ddv
 * @since : 2019/4/30 上午11:10
 */
@Component
public class LoginService implements ILoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Override
	public void login(USession session, CM_UserLogin request) {

		SM_LoginSuccess sm = new SM_LoginSuccess();
		PacketProtocol protocol = PacketProtocol.valueOf(sm);
		session.putSessionAttribute("accountId", request.getAccountId());

		PacketUtil.send(session, protocol);

		ConcurrentHashMap<Channel, USession> sessionMap = SessionManager.getSessionMap();

		logger.debug("1");
	}

	@Override
	public void logout(USession session, CM_UserLogout request) {

	}
}
