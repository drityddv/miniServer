package net.client;

import java.util.Scanner;
import java.util.concurrent.Executors;

import middleware.manager.ClazzManager;
import net.model.PacketProtocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:49
 */

public class ClientHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final String MESSAGE = "1: 账号登录\n" +
			"2: 创建账号\n" +
			"3: 进入地图\n" +
			"4: 后台命令";

	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	private Scanner scanner = new Scanner(System.in);

	private ClientDispatch dispatch = new ClientDispatch();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
		Object object = ClazzManager.readObjectById(protocol.getData(), protocol.getId());
		logger.info("客户端收到消息 : " + object.toString());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("客户端与服务端通讯成功!");

		Executors.newSingleThreadExecutor().submit(() -> {
			while (true) {
				logger.info("请选择操作!\n" + MESSAGE);
				dispatch.handler(ctx, scanner, scanner.nextInt());
				Thread.sleep(1000);
			}
		});
	}
}
