package game.user.login.facade;

import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.SM_LoginSuccess;
import io.netty.channel.Channel;
import middleware.anno.HandlerAnno;
import net.model.PacketProtocol;
import net.model.USession;
import net.utils.ProtoStuffUtil;
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

	/**
	 * 用户登录
	 *
	 * @param session
	 * @param request
	 */
	@HandlerAnno
	public void userLogin(USession session, CM_UserLogin request) {
		logger.info("user login method invoked");
		Channel channel = session.getChannel();
		SM_LoginSuccess sm = new SM_LoginSuccess();
		PacketProtocol protocol = new PacketProtocol();
		protocol.setId(3);
		protocol.setData(ProtoStuffUtil.serialize(sm));
		protocol.setLength(protocol.getData().length);

		channel.writeAndFlush(protocol);
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
