package game.base.game.attribute.computer;

import game.base.game.attribute.AttributeType;

/**
 * @author : ddv
 * @since : 2019/6/27 上午10:05
 */

public abstract class AbstractAttributeComputer implements IAttributeComputer {

    private AttributeType attributeType;

    public AbstractAttributeComputer(AttributeType type) {
        this.attributeType = type;
    }

    @Override
    public AttributeType getAttributeType() {
        return this.attributeType;
    }
}
