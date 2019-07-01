package game.base.game.attribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 不可修改的属性模型
 *
 * @author : ddv
 * @since : 2019/7/1 下午12:10
 */

public class LockAttribute extends Attribute {

    public static LockAttribute wrapper(Attribute attribute) {
        LockAttribute lockAttribute = new LockAttribute();
        lockAttribute.value = attribute.value;
        lockAttribute.attributeType = attribute.attributeType;
        return lockAttribute;
    }

    // return LockAttribute
    public static List<Attribute> wrapper(List<Attribute> attrs) {
        if (attrs == null) {
            return Collections.emptyList();
        }
        List<Attribute> attributes = new ArrayList<>();
        for (Attribute attribute : attrs) {
            attributes.add(wrapper(attribute));
        }
        return attributes;
    }

    @Override
    public void alter(long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAttributeType(AttributeType attributeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValue(long value) {
        throw new UnsupportedOperationException();
    }
}
