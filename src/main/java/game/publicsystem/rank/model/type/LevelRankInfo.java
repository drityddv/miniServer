package game.publicsystem.rank.model.type;

import game.publicsystem.rank.constant.RankType;
import spring.SpringContext;

/**
 * 等级排行数据
 *
 * @author : ddv
 * @since : 2019/8/5 9:48 PM
 */

public class LevelRankInfo extends BaseRankInfo {
    public LevelRankInfo(String id, long value) {
        super(id, value);
    }

    public static LevelRankInfo valueOf(String accountId, long level) {
        return new LevelRankInfo(accountId, level);
    }

    @Override
    public RankType getType() {
        return RankType.Player_Level;
    }

    @Override
    public void init() {
        this.value = SpringContext.getPlayerService().getPlayerByAccountId(id).getLevel();
    }
}
