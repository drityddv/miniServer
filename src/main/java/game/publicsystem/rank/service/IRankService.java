package game.publicsystem.rank.service;

import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.model.ServerRank;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.role.player.model.Player;

/**
 * 排行榜接口
 *
 * @author : ddv
 * @since : 2019/7/30 3:08 PM
 */

public interface IRankService {
    /**
     * 排行榜初始化
     */
    void init();

    /**
     * 往排行版添加信息
     *
     * @param baseRankInfo
     */
    void addRankInfo(BaseRankInfo baseRankInfo);

    /**
     * 添加排行榜信息 同时callback当前段位
     *
     * @param player
     * @param baseRankInfo
     */
    void addRankInfoCallback(Player player, BaseRankInfo baseRankInfo);

    /**
     * 玩家获取排行榜信息
     *
     * @param player
     * @return
     */
    ServerRank getServerRankInfo(Player player);

    /**
     * 保存数据
     */
    void saveRankInfo();

    /**
     * 更新cache
     */
    void updateCache();

    /**
     * 获取玩家对应模块的排行榜缓存
     *
     * @param rankType
     * @param id
     * @return
     */
    BaseRankInfo getPlayerCacheRankInfo(RankType rankType, String id);

    /**
     * 获取排行榜信息 start 从0开始
     *
     * @param player
     * @param rankType
     * @param start
     * @param end
     */
    void getRankInfo(Player player, RankType rankType, int start, int end);
}
