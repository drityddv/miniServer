package net.client;

import game.user.login.packet.CM_UserLogin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import middleware.manager.ClazzManager;
import net.model.PacketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:49
 */

public class ClientHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	private Scanner scanner = new Scanner(System.in);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
		Object object = ClazzManager.readObjectById(protocol.getData(), protocol.getId());
		logger.info("client handler : " + object.toString());

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("请输入登陆账号和密码进行登录!");

		CM_UserLogin request = new CM_UserLogin();
		request.setAccountId(scanner.next());
		request.setPassword(scanner.next());

		PacketProtocol protocol = PacketProtocol.valueOf(request);

		ctx.writeAndFlush(protocol);

	}
}
