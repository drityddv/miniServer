package game.base.game.attribute;

import game.base.game.attribute.util.AttributeUtils;
import game.base.object.AbstractCreature;

/**
 * @author : ddv
 * @since : 2019/6/27 下午2:34
 */

public abstract class CreatureAttributeContainer<T extends AbstractCreature> extends AttributeContainer<T> {

    public CreatureAttributeContainer(T owner) {
        super(owner);
    }

    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSyn) {
        AttributeUtils.compute(modelAttributeSet, finalAttributes, records, accumulateAttributes, getOwner());
    }

}
