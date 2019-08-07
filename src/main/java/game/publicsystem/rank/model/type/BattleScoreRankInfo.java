package game.publicsystem.rank.model.type;

import game.publicsystem.rank.constant.RankType;
import game.role.player.model.Player;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/8/5 4:39 PM
 */

public class BattleScoreRankInfo extends BaseRankInfo<String> {

    public BattleScoreRankInfo(String accountId, long battleScore) {
        super(accountId, battleScore);
    }

    public static BattleScoreRankInfo valueOf(Player player) {
        BattleScoreRankInfo rankInfo = new BattleScoreRankInfo(player.getAccountId(), player.getBattleScore());
        return rankInfo;
    }

    @Override
    public RankType getType() {
        return RankType.Battle_Score;
    }

    @Override
    public void init() {
        value = SpringContext.getPlayerService().loadPlayer(id).getBattleScore();
    }

}
