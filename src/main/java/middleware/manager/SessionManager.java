package middleware.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import net.model.USession;
import utils.SimpleUtil;

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
     * channel-session对应 后期需要增加sessionMap的过期清除,session的单点注册功能
     */
    private static Map<Channel, USession> sessionMap = new ConcurrentHashMap<>();

    /**
     * accountId - session
     */
    private static Map<String, USession> playerSession = new ConcurrentHashMap<>();

    public static void registerSession(USession session) {
        Channel channel = session.getChannel();
        String accountId = SimpleUtil.getAccountIdFromSession(session);

        if (sessionMap.containsKey(channel)) {
            logger.error("重复注册的session[{}]", channel.id().asLongText());
            return;
        }

        sessionMap.put(channel, session);

    }

    public static void registerPlayerSession(String accountId, USession session) {
        if (playerSession.containsKey(accountId)) {
            logger.error("重复注册的accountId[{}]", accountId);
            return;
        }
        playerSession.put(accountId, session);
    }

    public static void removeSession(Channel channel) {
        sessionMap.remove(channel);
    }

    public static void removePlayerSession(String accountId) {
        playerSession.remove(accountId);
    }

    public static boolean isContainSession(Channel channel) {
        return sessionMap.containsKey(channel);
    }

    public static USession getSession(Channel channel) {
        return sessionMap.get(channel);
    }

    public static Map<Channel, USession> getSessionMap() {
        return sessionMap;
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
