package net.client;

import game.user.login.packet.CM_UserLogin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import middleware.manager.ClazzManager;
import net.model.PacketProtocol;
import net.utils.ProtoStuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:49
 */

public class ClientHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
		logger.info("client received : " + protocol.toString());

		Object object = ClazzManager.readObjectById(protocol.getData(), protocol.getId());

		logger.info("client handler : " + object.toString());

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		PacketProtocol protocol = new PacketProtocol();

		protocol.setId(1);

		CM_UserLogin request = new CM_UserLogin();
		protocol.setData(ProtoStuffUtil.serialize(request));
		protocol.setLength(protocol.getData().length);

		ctx.writeAndFlush(protocol);

	}
}
