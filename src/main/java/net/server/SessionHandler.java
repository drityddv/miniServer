package net.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.manager.ClazzManager;
import game.base.manager.SessionManager;
import game.base.message.I18N;
import game.base.message.packet.SM_Message;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserRegister;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.model.PacketProtocol;
import net.model.USession;

/**
 * @author : ddv
 * @since : 2019/4/30 上午10:27
 */

public class SessionHandler extends SimpleChannelInboundHandler<PacketProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol protocol) throws Exception {
        int id = protocol.getId();
        Object object = ClazzManager.readObjectById(protocol.getData(), id);

        Channel channel = ctx.channel();
        String accountId = null;
        try {
            USession session = SessionManager.getSession(channel);
            accountId = (String)session.getSessionAttribute("accountId");
        } catch (NullPointerException e) {
            logger.error("当前会话session已经失效,请重新连接...");
            return;
        }

        // 登陆之后才会设置accountId属性 这里可以对登陆,注册
        if (accountId != null || object.getClass() == CM_UserLogin.class
            || object.getClass() == CM_UserRegister.class) {
            ctx.fireChannelRead(protocol);
            return;
        }

        ctx.writeAndFlush(PacketProtocol.valueOf(SM_Message.valueOf(I18N.ILLEGAL_SESSION)));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        USession session = USession.createSession(ctx.channel());
        SessionManager.registerSession(session);
        ctx.fireChannelActive();
    }
}
