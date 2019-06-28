package game.base.game.attribute.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.AttributeUpdateRecords;
import game.base.game.attribute.CreatureAttributeContainer;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/27 下午3:26
 */

public class PlayerAttributeContainer extends CreatureAttributeContainer<Player> {

    /**
     * 当前属性(不包含buff)
     */
    private Map<AttributeType, Attribute> finalAttrExcludeBuff = new ConcurrentHashMap<>();

    private Map<AttributeType, Attribute> originAttributes = new ConcurrentHashMap<>();

    public PlayerAttributeContainer(Player owner) {
        super(owner);
    }

    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSyn) {
        super.recompute(records, needSyn);
    }
}
