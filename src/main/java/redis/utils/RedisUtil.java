package redis.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * @author : ddv
 * @since : 2019/7/22 6:16 PM
 */

public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    public static final String EXPIRED_QUEUE = "__keyevent@0__:expired";

    private static Jedis jedis;

    public static volatile int num = 0;

    public static void init() {
        jedis = new Jedis("localhost", 6379);
        jedis.expire("ddv", 0);
        // String parameter = "notify-keyspace-events";
        // List<String> notify = jedis.configGet(parameter);
        // if (notify.get(1).equals("")) {
        // jedis.configSet(parameter, "Ex");
        // }
        // jedis.subscribe(new JedisPubSub() {
        // @Override
        // public void onPMessage(String pattern, String channel, String message) {
        // logger.info("订阅消息:[{}]", message);
        // }
        // }, EXPIRED_QUEUE);
    }

    public static void add() {
        num = num + 1;
    }

    public static boolean lock(String key, String value) {
        Long success = jedis.setnx(key, value);
        // logger.info("线程[{}] 申请锁结果[{}]", Thread.currentThread().getName(), success == 1);
        // if (success.intValue() == 1) {
        // logger.info("线程[{}] 申请锁成功", Thread.currentThread().getName());
        // }
        return success.intValue() == 1;
    }

    public static void unLock(String key) {
        jedis.expire(key, 0);
    }

    public static void main(String[] args) throws InterruptedException {
        RedisUtil.init();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ThreadPoolExecutor[] threadPoolExecutors = new ThreadPoolExecutor[10];
        for (int i = 0; i < 10; i++) {
            threadPoolExecutors[i] =
                new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }
        // ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            threadPoolExecutors[i % 10].submit(() -> {
//                logger.info("线程[{}]", Thread.currentThread().getName());
                boolean success = false;
                while (!success) {
                    logger.info("[{}]尝试重新获取锁", Thread.currentThread().getName());
                    success = RedisUtil.lock("ddv", "ddv");
                     try {
                     Thread.sleep(100);
                     } catch (InterruptedException e) {
                     e.printStackTrace();
                     }
                }
                RedisUtil.add();
                logger.info("线程[{}] 拿到锁 num[{}]", Thread.currentThread().getName(), num);
                jedis.expire("ddv", 0);

                logger.info("线程[{}] 释放锁", Thread.currentThread().getName());
            });
        }
        countDownLatch.await();
    }

    // public static void main(String[] args) {
    // Jedis jedis = new Jedis("localhost", 6379);
    // jedis.zadd("battleScore", 999.10, "Alan Kay");
    // jedis.zadd("battleScore", 1953, "Richard Stallman");
    // jedis.zadd("battleScore", 1965, "Yukihiro Matsumoto");
    // jedis.zadd("battleScore", 1916, "Claude Shannon");
    // jedis.zadd("battleScore", 1969, "Linus Torvalds");
    // jedis.zadd("battleScore", 1912, "Alan Turing");
    // Set<String> setValues = jedis.zrange("battleScore", 0, -1);
    // System.out.println(setValues);
    // Set<String> setValues2 = jedis.zrevrange("hackers", 0, -1);
    // System.out.println(setValues2);
    // }
}
