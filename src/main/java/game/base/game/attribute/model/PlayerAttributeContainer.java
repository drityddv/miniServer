package game.base.game.attribute.model;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import game.base.game.attribute.AttributeUpdateRecords;
import game.base.game.attribute.CreatureAttributeContainer;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/27 下午3:26
 */

public class PlayerAttributeContainer extends CreatureAttributeContainer<Player> {

    // FIXME protoStuff如果没有默认构造器的机制不清楚 后面再补
    // 这个是给protoStuff走的 反序列化走构造函数
    public PlayerAttributeContainer() {
        super(null);
        finalAttributes = new ConcurrentHashMap<>();
        modelAttributeSet = new HashMap<>();
        accumulateAttributes = new HashMap<>();
    }

    public PlayerAttributeContainer(Player owner) {
        super(owner);
        finalAttributes = new ConcurrentHashMap<>();
        modelAttributeSet = new HashMap<>();
        accumulateAttributes = new HashMap<>();
    }

    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSyn) {
        super.recompute(records, needSyn);
    }

}
