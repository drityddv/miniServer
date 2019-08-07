package game.publicsystem.rank.model.rank;

import game.publicsystem.rank.constant.RankType;

/**
 * 等级排行榜
 *
 * @author : ddv
 * @since : 2019/8/6 11:34 AM
 */

public class LevelRank extends AbstractRank<String> {
    public LevelRank() {
        super();
    }

    @Override
    public RankType getRankType() {
        return RankType.Player_Level;
    }

}
