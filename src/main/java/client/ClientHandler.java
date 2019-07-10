package client;

import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import client.anno.Action;
import game.base.manager.ClazzManager;
import game.user.login.packet.CM_UserLogin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.model.PacketProtocol;
import utils.ClassUtil;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:49
 */

public class ClientHandler extends SimpleChannelInboundHandler<PacketProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    public String username;
    private Scanner scanner = new Scanner(System.in);
    private ClientDispatch dispatch = new ClientDispatch();

    public static ClientHandler valueOf(String username) {
        ClientHandler clientHandler = new ClientHandler();
        clientHandler.username = username;
        return clientHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
        Object object = ClazzManager.readObjectById(protocol.getData(), protocol.getId());
        Method action = ClassUtil.getMethodByAnnotation(object, Action.class);
        if (action == null) {
            System.out.println("客户端收到消息 : " + object.toString());
        } else {
            action.setAccessible(true);
            ReflectionUtils.invokeMethod(action, object);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CM_UserLogin cm = new CM_UserLogin();
        cm.setAccountId(username);
        cm.setPassword(username);
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
