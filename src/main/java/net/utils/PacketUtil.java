package net.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import middleware.manager.ClazzManager;
import net.model.PacketProtocol;
import net.model.USession;

/**
 * 发包工具
 *
 * @author : ddv
 * @since : 2019/4/30 下午2:15
 */
public class PacketUtil {

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
}
