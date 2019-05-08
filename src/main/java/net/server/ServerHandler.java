package net.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {

        try {
            Object packet = ClazzManager.readObjectById(protocol.getData(), protocol.getId());
            logger.info("server handler receive: [{}]", packet.toString());
            SpringContext.getDispatcher().invoke(SessionManager.getSession(ctx.channel()), packet);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("dispatcher invoke error.");
        }

    }
}
