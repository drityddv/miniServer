package net.client;

import java.util.Scanner;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.user.login.packet.CM_UserLogin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import middleware.manager.ClazzManager;
import net.model.PacketProtocol;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:49
 */

public class ClientHandler extends SimpleChannelInboundHandler<PacketProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private Scanner scanner = new Scanner(System.in);

    private ClientDispatch dispatch = new ClientDispatch();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
        Object object = ClazzManager.readObjectById(protocol.getData(), protocol.getId());
        System.out.println("客户端收到消息 : " + object.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端与服务端通讯成功!");
        logger.info("示例输入如下：\nsend 1 ddv ddv");
        CM_UserLogin cm = new CM_UserLogin();
        cm.setAccountId("ddv");
        cm.setPassword("ddv");
        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                dispatch.handler(ctx, scanner.nextLine());
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("服务器主动断开连接...");
        System.exit(1);
    }
}
