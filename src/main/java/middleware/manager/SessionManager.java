package middleware.manager;

import io.netty.channel.Channel;
import net.model.USession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

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
	 * ip-session对应
	 */
	private static ConcurrentHashMap<Channel, USession> sessionMap = new ConcurrentHashMap<>();

	public static void registerSession(USession session) {
		Channel channel = session.getChannel();

		if (sessionMap.contains(channel)) {
			logger.error("重复注册的session[{}]", channel.id().asLongText());
			return;
		}

		sessionMap.put(channel, session);
	}

	public static void removeSession(Channel channel) {
		sessionMap.remove(channel);
	}

	public static boolean isContainSession(Channel channel) {
		return sessionMap.contains(channel);
	}

	public static USession getSession(Channel channel) {
		return sessionMap.get(channel);
	}
}
