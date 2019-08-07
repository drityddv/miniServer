package redis.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Component;

import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.publicsystem.rank.model.type.BattleScoreRankInfo;
import game.role.player.model.Player;
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

    @Override
    public void addRankInfo(Player player, BaseRankInfo<String> rankInfo) {
        jedis.zadd(rankInfo.getType().name(), rankInfo.getValue(), rankInfo.getId());
    }

    @Override
    public Collection<BaseRankInfo> getRankInfo(Player player, RankType rankType) {
        String rankName = rankType.name();
        Set<String> memberSet = jedis.zrevrange(rankName, 0, -1);
        memberSet.forEach(key -> {
            if (rankType == RankType.Battle_Score) {
                BattleScoreRankInfo rankInfo = new BattleScoreRankInfo(key, (jedis.zscore(rankName, key)).longValue());
            }
        });

        return null;
    }

}
