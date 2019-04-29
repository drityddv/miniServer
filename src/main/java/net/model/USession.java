package net.model;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : ddv
 * @since : 2019/4/28 下午9:50
 */

public class USession {
	private static final Logger LOGGER = LoggerFactory.getLogger(USession.class);

	private String accountId;
	private long createdAt;
	private Channel channel;
	private ConcurrentHashMap<String, Object> attributes;

	public static USession createSession(String accountId, Channel channel) {
		USession session = new USession();
		session.setAccountId(accountId);
		session.setChannel(channel);
		session.setCreatedAt(Instant.now().toEpochMilli());
		return session;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	@Override
	public String toString() {
		return "USession{" +
				"accountId='" + accountId + '\'' +
				", createdAt=" + createdAt +
				", channel=" + channel +
				'}';
	}
}
