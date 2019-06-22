package game.base.game.attribute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : ddv
 * @since : 2019/6/20 下午10:23
 */

public class AttributeContainer {

    // 玩家id
    private long id;
    // 基础属性容器
    private Map<AttributeType, AttributeSquare> attributes;

    private AttributeContainer() {

    }

    public static AttributeContainer valueOf(long id) {
        AttributeContainer attributeContainer = new AttributeContainer();

		attributeContainer.id = id;
        AttributeType[] attributeTypes = AttributeType.values();
        attributeContainer.attributes = new ConcurrentHashMap<>(attributeTypes.length);

        for (AttributeType attributeType : attributeTypes) {
            attributeContainer.attributes.put(attributeType, AttributeSquare.valueOf(attributeType));
        }

        return attributeContainer;
    }

    // get and set
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<AttributeType, AttributeSquare> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, AttributeSquare> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "AttributeContainer{" + "id=" + id + ", attributes=" + attributes + '}';
    }
}
