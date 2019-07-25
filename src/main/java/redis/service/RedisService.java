package redis.service;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * @author : ddv
 * @since : 2019/7/22 6:16 PM
 */
@Component
public class RedisService {

    private static Jedis jedis;

    public void init() {
        jedis = new Jedis("localhost", 6379);
    }

}
