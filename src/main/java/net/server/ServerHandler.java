package net.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import middleware.manager.ClazzManager;
import middleware.manager.SessionManager;
import net.model.PacketProtocol;
import net.model.USession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.SpringContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : ddv
 * @date : 2019/3/3 上午12:36
 */

public class ServerHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
		logger.info("server handler receive: [{}]", protocol.toString());

		try {
			Object packet = ClazzManager.readObjectById(protocol.getData(), protocol.getId());
			SpringContext.getDispatcher().invoke(SessionManager.getSession(ctx.channel()), packet);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dispatcher invoke error.");
		}

	}
}
