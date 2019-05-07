package net.model;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.TimeUtil;
import io.netty.channel.Channel;

/**
 * @author : ddv
 * @since : 2019/4/28 下午9:50
 */

public class USession {
	private static final Logger LOGGER = LoggerFactory.getLogger(USession.class);

	private long createdAt;
	private Channel channel;
	private ConcurrentHashMap<String, Object> attributes;

	public static USession createSession(Channel channel) {
		USession session = new USession();
		session.setChannel(channel);
		session.setCreatedAt(TimeUtil.now());
		session.setAttributes(new ConcurrentHashMap<>(16));
		return session;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ConcurrentHashMap<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(ConcurrentHashMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Object getSessionAttribute(String key) {
		return attributes.get(key);
	}

	public void putSessionAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	@Override
	public String toString() {
		return "USession{" +
				"createdAt=" + createdAt +
				", channel=" + channel +
				", attributes=" + attributes +
				'}';
	}
}
