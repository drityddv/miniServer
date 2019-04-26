package net.server;

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
		msg.setData("fuck".getBytes());
		msg.setLength("fuck".getBytes().length);
		msg.setId((byte) 2);

		ctx.writeAndFlush(msg);
	}
}
