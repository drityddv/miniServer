package redis.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.publicsystem.rank.model.type.BattleScoreRankInfo;
import game.role.player.model.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author : ddv
 * @since : 2019/7/22 6:16 PM
 */
@Component
public class RedisService implements IRedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private static GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    private static Jedis jedis;

    @Override
    public void init() {
        // jedis = new Jedis("127.0.0.1", 6379);
        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379, 0, null);
        jedis = jedisPool.getResource();
        logger.info("redis初始化完成");

    }

    @Override
    public void addRankInfo(Player player, BaseRankInfo rankInfo) {
        jedis.zadd(rankInfo.getType().name(), rankInfo.getValue(), rankInfo.getId());
    }

    @Override
    public Collection<BaseRankInfo> getRankInfo(Player player, RankType rankType) {
        String rankName = rankType.name();
        List<BaseRankInfo> rankInfoList = new ArrayList<>();
        Set<String> memberSet = jedis.zrevrange(rankName, 0, -1);
        memberSet.forEach(key -> {
            if (rankType == RankType.Battle_Score) {
                BattleScoreRankInfo rankInfo = new BattleScoreRankInfo(key, (jedis.zscore(rankName, key)).longValue());
                rankInfoList.add(rankInfo);
            }
        });
        return rankInfoList;
    }

}
