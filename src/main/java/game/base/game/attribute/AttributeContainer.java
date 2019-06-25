package game.base.game.attribute;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.base.game.player.PlayerModel;

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

    public void reCompute(Attribute attribute) {
        Iterator<Map.Entry<AttributeType, AttributeSquare>> iterator = attributes.entrySet().iterator();

        PlayerModel model = attribute.getModel();
        Map<AttributeType, Long> newModelValue = attribute.getModelValue();

        while (iterator.hasNext()) {
            Map.Entry<AttributeType, AttributeSquare> next = iterator.next();
            AttributeType key = next.getKey();
            AttributeSquare square = next.getValue();

            Map<PlayerModel, Long> modelValue = square.getModelValue();
            if (modelValue != null) {
                modelValue.put(model, newModelValue.get(key));
            }

            square.reCompute();
        }

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
