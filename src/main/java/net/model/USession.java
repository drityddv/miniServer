package net.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/4/28 下午9:50
 */

public class USession {
    private static final Logger LOGGER = LoggerFactory.getLogger(USession.class);

    private volatile long createdAt;
    private volatile Channel channel;
    private volatile Map<String, Object> attributes;

    public static USession createSession(Channel channel) {
        USession session = new USession();
        session.channel = channel;
        session.createdAt = TimeUtil.now();
        session.attributes = new ConcurrentHashMap<>(16);
        return session;
    }

    public Channel getChannel() {
        return channel;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object getSessionAttribute(String key) {
        return attributes.get(key);
    }

    public void putSessionAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public String toString() {
        return "USession{" + "createdAt=" + createdAt + ", channel=" + channel + ", attributes=" + attributes + '}';
    }
}
