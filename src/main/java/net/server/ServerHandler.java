package net.server;

import middleware.manager.ClazzManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.model.PacketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @date : 2019/3/3 上午12:36
 */

public class ServerHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol msg) throws Exception {
		logger.info(msg.toString());

		Class clazz = ClazzManager.getClazz((int) msg.getId());
		Object object = ClazzManager.readObjectFromBytes(msg.getData(), clazz);
		logger.info(object.getClass().getName());
		logger.info(object.toString());
	}
}
