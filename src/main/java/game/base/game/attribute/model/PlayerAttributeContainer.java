package game.base.game.attribute.model;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeUpdateRecords;
import game.base.game.attribute.CreatureAttributeContainer;
import game.publicsystem.rank.model.type.BattleScoreRankInfo;
import game.role.player.model.Player;
import game.world.fight.syncStrategy.impl.AttributeSynStrategy;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/6/27 下午3:26
 */

public class PlayerAttributeContainer extends CreatureAttributeContainer<Player> {

    // 这个是给protoStuff走的 反序列化走构造函数
    public PlayerAttributeContainer() {
        super(null);
        finalAttributes = new ConcurrentHashMap<>();
        modelAttributeSet = new ConcurrentHashMap<>();
        accumulateAttributes = new HashMap<>();
    }

    public PlayerAttributeContainer(Player owner) {
        super(owner);
        finalAttributes = new ConcurrentHashMap<>();
        modelAttributeSet = new ConcurrentHashMap<>();
        accumulateAttributes = new HashMap<>();
    }

    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSyn) {
        super.recompute(records, needSyn);
        if (needSyn) {
            AttributeSynStrategy synStrategy = AttributeSynStrategy.valueOf(owner);
            synStrategy.init(owner);
            owner.fighterSync(synStrategy);
        }

        if (calculateBattleScore()) {
            BattleScoreRankInfo rankInfo = BattleScoreRankInfo.valueOf(owner);
            rankInfo.random();
            SpringContext.getRankService().addRankInfo(rankInfo);
            SpringContext.getRedisService().addRankInfo(owner, rankInfo);
        }
    }

    // 计算战斗力 返回是否变化
    public boolean calculateBattleScore() {
        long oldScore = owner.getBattleScore();
        long newScore = 0;
        for (AttributeSet attributeSet : modelAttributeSet.values()) {
            newScore += attributeSet.getBattleScore();
        }

        owner.setBattleScore(newScore);
        if (newScore != oldScore) {
            return true;
        }
        // 写死
        return true;
    }

}
