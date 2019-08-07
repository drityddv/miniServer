import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

import game.publicsystem.rank.model.DefaultComparator;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.publicsystem.rank.model.type.BattleScoreRankInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.publicsystem.alliance.model.Alliance;
import game.publicsystem.alliance.model.ServerAllianceInfo;
import game.role.player.model.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.model.subscribe.Subscriber;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    private volatile int num = 0;

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
		ConcurrentSkipListMap<BaseRankInfo,Object> rankDataMap =
				new ConcurrentSkipListMap<>(new DefaultComparator());

		Set<BaseRankInfo> baseRankInfoSet = new HashSet<>();

		BattleScoreRankInfo rankInfo = new BattleScoreRankInfo("1",999);
		BattleScoreRankInfo rankInfo1 = new BattleScoreRankInfo("2",999);
		BattleScoreRankInfo rankInfo2 = new BattleScoreRankInfo("3",999);
		BattleScoreRankInfo rankInfo3 = new BattleScoreRankInfo("1",3);
		BattleScoreRankInfo rankInfo4 = new BattleScoreRankInfo("1",4);

		rankDataMap.put(rankInfo,rankInfo);
		rankDataMap.put(rankInfo1,rankInfo1);
		rankDataMap.put(rankInfo2,rankInfo2);
		rankDataMap.put(rankInfo3,rankInfo3);
		rankDataMap.put(rankInfo4,rankInfo4);

		baseRankInfoSet.add(rankInfo);
		baseRankInfoSet.add(rankInfo1);
		baseRankInfoSet.add(rankInfo2);
		baseRankInfoSet.add(rankInfo3);
		baseRankInfoSet.add(rankInfo4);


    }

}
