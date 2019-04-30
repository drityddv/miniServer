package net.server;

import game.user.login.packet.CM_UserLogin;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import middleware.manager.ClazzManager;
import middleware.manager.SessionManager;
import net.model.PacketProtocol;
import net.model.USession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/4/30 上午10:27
 */

public class SessionHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
		logger.info("session handler invoke...");
		int id = protocol.getId();
		Object object = ClazzManager.readObjectById(protocol.getData(), id);

		Channel channel = ctx.channel();

		USession session = SessionManager.getSession(channel);

		String accountId = (String) session.getSessionAttribute("accountId");

		// 登陆之后才会设置accountId属性 这里可以对登陆放行
		if (accountId == null) {
			if (object.getClass() == CM_UserLogin.class) {
				ctx.fireChannelRead(protocol);
			}
		}

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		USession session = USession.createSession(ctx.channel());
		SessionManager.registerSession(session);
		ctx.fireChannelActive();
	}
}
