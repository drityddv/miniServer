package redis.utils;

import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @author : ddv
 * @since : 2019/7/22 6:16 PM
 */

public class RedisUtil {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.zadd("hackers", 1940, "Alan Kay");
        jedis.zadd("hackers", 1953, "Richard Stallman");
        jedis.zadd("hackers", 1965, "Yukihiro Matsumoto");
        jedis.zadd("hackers", 1916, "Claude Shannon");
        jedis.zadd("hackers", 1969, "Linus Torvalds");
        jedis.zadd("hackers", 1912, "Alan Turing");
        Set<String> setValues = jedis.zrange("hackers", 0, -1);
        System.out.println(setValues);
        Set<String> setValues2 = jedis.zrevrange("hackers", 0, -1);
        System.out.println(setValues2);
    }
}
