package game.base.game.attribute;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import game.base.game.attribute.id.AttributeId;
import utils.CollectionUtil;

/**
 * @author : ddv
 * @since : 2019/6/27 下午3:40
 */

public class AttributeUpdateRecords {

    /**
     * 改变的模块
     */
    private final AttributeId attributeId;

    /**
     * 此次改变涉及的属性类型
     */
    private final Set<AttributeType> types = new HashSet<>();

    /**
     * 本次被移除的属性类型
     */
    private Collection<Attribute> removedAttributes = null;

    public AttributeUpdateRecords(AttributeId attributeId) {
        this.attributeId = attributeId;
    }

    public void addAttrs(Collection<Attribute> attrs) {
        if (CollectionUtil.isNotBlank(attrs)) {
            for (Attribute attr : attrs) {
                addType(attr.getAttributeType());
            }
        }
    }

    private void addType(AttributeType type) {
        if (type == null) {
            return;
        }
        types.add(type);
        AttributeType[] effectAttrs = type.getEffectAttrs();
        if (effectAttrs != null) {
            for (AttributeType attributeType : effectAttrs) {
                addType(attributeType);
            }
        }
    }

    public AttributeId getAttributeId() {
        return attributeId;
    }

    public Set<AttributeType> getTypes() {
        return types;
    }

    public Collection<Attribute> getRemovedAttributes() {
        return removedAttributes;
    }

    public void setRemovedAttributes(Collection<Attribute> removedAttributes) {
        this.removedAttributes = removedAttributes;
    }
}
