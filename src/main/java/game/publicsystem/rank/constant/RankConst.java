package game.publicsystem.rank.constant;

/**
 * @author : ddv
 * @since : 2019/8/6 10:31 AM
 */

public interface RankConst {
    /**
     * 最大上榜数量 预留200区间
     */
    int MAX_SIZE = 1000;
    /**
     * 战力最大值 十万
     */
    long BATTLE_SCORE_MAX = 10000;
    /**
     * 战力区间个数 动态分配 0:[0~9999] 1:[10000~19999]
     */
    int BATTLE_RANK_SEGMENT = 10;
    /**
     * 等级区间个数 当前最大等级为10级
     */
    int LEVEL_RANK_SEGMENT = 5;
    /**
     * 等级最大值
     */
    int LEVEL_MAX = 10;
}
