package game.base.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import net.model.USession;

/**
 * 用户session管理
 *
 * @author : ddv
 * @since : 2019/4/29 下午9:34
 */
@Component
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    /**
     * channel - session netty连接建立就会注册
     */
    private static Map<Channel, USession> sessionMap = new ConcurrentHashMap<>();

    /**
     * accountId - session 登陆成功才会注册
     */
    private static Map<String, USession> playerSession = new ConcurrentHashMap<>();

    public static void registerSession(USession session) {
        Channel channel = session.getChannel();
        sessionMap.put(channel, session);
    }

    public static void registerPlayerSession(String accountId, USession session) {
        if (playerSession.containsKey(accountId)) {
            logger.error("重复注册的session,accountId[{}]", accountId);
            return;
        }
        playerSession.put(accountId, session);
    }

    // 玩家是否在线
    public static boolean isPlayerOnline(String accountId) {
        return playerSession.containsKey(accountId);
    }

    public static void removeSession(Channel channel) {
        sessionMap.remove(channel);
    }

    public static void removePlayerSession(String accountId) {
        playerSession.remove(accountId);
    }

    public static USession getSession(Channel channel) {
        return sessionMap.get(channel);
    }

    public static USession getSessionByAccountId(String accountId) {
        Iterator<Map.Entry<Channel, USession>> iterator = sessionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Channel, USession> entry = iterator.next();
            USession session = entry.getValue();
            if (accountId.equals(session.getAttributes().get("accountId"))) {
                return session;
            }
        }
        return null;
    }
}
