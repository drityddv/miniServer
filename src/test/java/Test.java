import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.publicsystem.alliance.model.Alliance;
import game.publicsystem.alliance.model.ServerAllianceInfo;
import game.publicsystem.rank.model.DefaultComparator;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.publicsystem.rank.model.type.BattleScoreRankInfo;
import game.role.player.model.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.model.subscribe.Subscriber;
import spring.SpringContext;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void run() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);
        // // 哨兵信息
        // Set<String> sentinels =
        // new HashSet<>(Arrays.asList("192.168.11.128:26379", "192.168.11.129:26379", "192.168.11.130:26379"));
        // // 创建连接池
        // JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig, "123456");
        // // // 获取客户端
        Jedis jedis = jedis = new Jedis("localhost", 6379);

        jedis.subscribe(new Subscriber(), "ddv");
        // // 执行两个命令
        // jedis.set("mykey", "myvalue");
        // String value = jedis.get("mykey");
        // System.out.println(value);
    }

    @org.junit.Test
    public void run1() {
        ServerAllianceInfo a = ServerAllianceInfo.valueOf(1);
        a.addAlliance(Alliance.valueOf(Player.valueOf("ddv"), "ddv"));

        byte[] serialize = ProtoStuffUtil.serialize(a);

        a = ProtoStuffUtil.deserialize(serialize, ServerAllianceInfo.class);
    }

    class A {
        private Map<Long, Alliance> memberMap = new HashMap<>();
    }

    class B {
        private long id;
        private String name;
        private Map<Long, Long> map;
    }

    @org.junit.Test
    public void run2() {

    }



}
