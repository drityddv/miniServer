package game.base.game.attribute.model;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import game.base.game.attribute.AttributeUpdateRecords;
import game.base.game.attribute.CreatureAttributeContainer;
import game.role.player.model.Player;
import game.world.fight.syncStrategy.impl.AttributeSynStrategy;

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
    }

}
