package net.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.MessageEnum;
import game.base.manager.ClazzManager;
import game.base.manager.SessionManager;
import game.base.message.packet.SM_Message;
import game.role.player.model.Player;
import net.model.PacketProtocol;
import net.model.USession;

/**
 * 发包工具
 *
 * @author : ddv
 * @since : 2019/4/30 下午2:15
 */
public class
PacketUtil {

    private static final Logger logger = LoggerFactory.getLogger(PacketUtil.class);

    /**
     * 这个方法 使用者不要包装PacketProtocol 否则会误杀!!!
     *
     * @param session
     * @param object
     */
    public static void send(USession session, Object object) {
        int id = ClazzManager.getIdByClazz(object.getClass());

        if (id == 0) {
            logger.error("无效的发送协议[id={},clazz={}]", id, object.getClass());
            return;
        }

        PacketProtocol protocol = PacketProtocol.valueOf(object);
        session.getChannel().writeAndFlush(protocol);
    }

    public static void send(Player player, int i18N) {
        send(player, SM_Message.valueOf(i18N));
    }

    public static void send(Player player, Object object) {
        if (object instanceof MessageEnum) {
            send(player, ((MessageEnum)object).getId());
            return;
        }
        USession session = SessionManager.getSessionByAccountId(player.getAccountId());
        if (session == null) {
            return;
        }
        send(session, object);
    }

}
