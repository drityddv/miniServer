package redis.service;

import java.util.Collection;

import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/26 8:07 PM
 */

public interface IRedisService {

    /**
     * 初始化
     */
    void init();

    /**
     * 向排行榜添加数据
     *
     * @param player
     * @param rankInfo
     */
    void addRankInfo(Player player, BaseRankInfo rankInfo);

    /**
     * 获取排行榜
     *
     * @param player
     * @param rankType
     * @return
     */
    Collection<BaseRankInfo> getRankInfo(Player player, RankType rankType);
}
