package game.publicsystem.rank.constant;

import java.util.HashMap;
import java.util.Map;

import game.publicsystem.rank.model.rank.AbstractRank;
import game.publicsystem.rank.model.rank.BattleScoreRank;
import game.publicsystem.rank.model.rank.LevelRank;

/**
 * @author : ddv
 * @since : 2019/8/5 9:29 PM
 */

public enum RankType {

    /**
     * 战力排行榜
     */
    Battle_Score(1) {
        @Override
        public AbstractRank createRank() {
            return new BattleScoreRank();
        }
    },

    /**
     * 等级排行榜
     */
    Player_Level(2) {
        @Override
        public AbstractRank createRank() {
            return new LevelRank();
        }
    },;

    private static Map<Integer, RankType> ID_TO_TYPE = new HashMap<>();

    static {
        for (RankType rankType : RankType.values()) {
            ID_TO_TYPE.put(rankType.id, rankType);
        }
    }

    int id;

    RankType(int id) {
        this.id = id;
    }

    public static RankType getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    public AbstractRank createRank() {
        return null;
    }

}
