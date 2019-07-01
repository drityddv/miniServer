package game.base.game.attribute;

/**
 * @author : ddv
 * @since : 2019/6/25 上午9:50
 */

public class Attribute {

    protected AttributeType attributeType;

	protected long value;

    public static Attribute valueOf(AttributeType attributeType, long value) {
        Attribute attribute = new Attribute();
        attribute.attributeType = attributeType;
        attribute.value = value;
        return attribute;
    }

    public void alter(long value) {
        attributeType.alter(this, value);
    }

    // get and set
    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
