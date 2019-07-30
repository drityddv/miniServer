package redis.model.subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

/**
 * 发布-订阅处理
 *
 * @author : ddv
 * @since : 2019/7/30 3:34 PM
 */

public class Subscriber extends JedisPubSub {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(String channel, String message) {
        logger.info("频道[{}] 消息[{}]", channel, message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("频道[{}] 订阅者数量[{}]", channel, subscribedChannels);
    }
}
