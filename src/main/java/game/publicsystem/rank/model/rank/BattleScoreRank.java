package game.publicsystem.rank.model.rank;

import game.publicsystem.rank.constant.RankType;

/**
 * 战力排行榜
 *
 * @author : ddv
 * @since : 2019/8/6 10:59 AM
 */

public class BattleScoreRank extends AbstractRank<String> {

    public BattleScoreRank() {
        super();
    }

    @Override
    public RankType getRankType() {
        return RankType.Battle_Score;
    }

}
