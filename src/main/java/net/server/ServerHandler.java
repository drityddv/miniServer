package net.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import middleware.manager.ClazzManager;
import middleware.manager.SessionManager;
import net.model.PacketProtocol;
import spring.SpringContext;

/**
 * @author : ddv
 * @date : 2019/3/3 上午12:36
 */
public class ServerHandler extends SimpleChannelInboundHandler<PacketProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            logger.info("channel超时30s,即将清除玩家数据...");
            SpringContext.getLoginService().logout(SessionManager.getSession(ctx.channel()), null);
            ctx.channel().close();
            return;
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {

        try {
            logger.info("在线玩家数目[{}]", channelGroup.size());
            Object packet = ClazzManager.readObjectById(protocol.getData(), protocol.getId());
            logger.info("server handler receive: [{}]", packet.toString());
            SpringContext.getDispatcher().invoke(SessionManager.getSession(ctx.channel()), packet);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("dispatcher invoke error.");
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
    }

}
