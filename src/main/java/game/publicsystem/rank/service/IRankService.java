package game.publicsystem.rank.service;

import game.publicsystem.rank.model.ServerRank;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.role.player.model.Player;

/**
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
}
