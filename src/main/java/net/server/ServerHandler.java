package net.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.manager.ClazzManager;
import game.base.manager.SessionManager;
import game.gm.packet.SM_LogMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.model.PacketProtocol;
import spring.SpringContext;
import utils.NetUtil;

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
            // // 跑到派发器去执行,这里是netty线程
            // logger.info("channel超时,即将清除玩家数据...");
            // USession session = SessionManager.getSession(ctx.channel());
            // String accountId = SessionUtil.getAccountIdFromSession(session);
            //
            // // 如果一直没登录 在这里干掉
            // if (accountId == null) {
            // SessionManager.removeSession(ctx.channel());
            // ctx.channel().close();
            // return;
            // }
            //
            // SpringContext.getDispatcher().invoke(session, new CM_UserLogout());

            Object object = SM_LogMessage.valueOf("server response" + NetUtil.getLocalIpAddress());
            PacketProtocol protocol = PacketProtocol.valueOf(object);
            ctx.writeAndFlush(protocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {

        try {
			Object object = SM_LogMessage.valueOf("server response " + NetUtil.getLocalIpAddress());
			PacketProtocol protocol1 = PacketProtocol.valueOf(object);
			ctx.writeAndFlush(protocol1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("dispatcher invoke error.");
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        channelGroup.add(ctx.channel());
    }

}
