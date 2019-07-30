package redis.service;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * @author : ddv
 * @since : 2019/7/22 6:16 PM
 */
@Component
public class RedisService implements IRedisService {

    private static Jedis jedis;

    @Override
    public void init() {
         jedis = new Jedis("localhost", 6379);
    }

}
