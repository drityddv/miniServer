package game.base.game.attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.base.game.attribute.id.AttributeId;
import game.base.game.attribute.util.AttributeUtils;

/**
 * 属性容器
 *
 * @author : ddv
 * @since : 2019/6/20 下午10:23
 */

public abstract class AttributeContainer<T> {

    /**
     * 属性容器拥有者
     */
    protected transient T owner;
    /**
     * 当前属性
     */
    protected Map<AttributeType, Attribute> finalAttributes = new ConcurrentHashMap<>();
    /**
     * 模块属性[基本属性,传世,雷霆等...]
     */
    protected Map<AttributeId, AttributeSet> modelAttributeSet = new HashMap<>();
    /**
     * 计算使用的变量
     */
    protected Map<AttributeType, Attribute> accumulateAttributes = new HashMap<>();

    /**
     * 将模块属性累加到属性集中
     *
     * @param model
     * @param attributeList
     */
    private void accumulateModelAttribute(AttributeId model, List<Attribute> attributeList) {
        // FIXME 如果增加了新模块 要注意改写哈希表方法
        AttributeSet attributeSet = modelAttributeSet.get(model);

        if (attributeSet == null) {
            attributeSet = new AttributeSet();
            modelAttributeSet.put(model, attributeSet);
        }
        attributeSet.getAttributeMap().clear();

        if (attributeList != null) {
            AttributeUtils.accumulateToMap(attributeList, attributeSet.getAttributeMap());
        }
    }

    public long getAttributeValue(AttributeType attributeType) {
        return getFinalAttributeValue(attributeType);
    }

    protected long getFinalAttributeValue(AttributeType attributeType) {
        return AttributeUtils.getAttributeValue(finalAttributes, attributeType);
    }

    /**
     * 设置模块属性,不重新计算
     *
     * @param id
     * @param attributes
     */
    public void putAttributes(AttributeId id, List<Attribute> attributes) {
        if (attributes == null) {
            return;
        }
        putAttributes(id, attributes, null);

    }

    /**
     * 这个是否触发修改 根据records是否为空决定
     */
    public void putAttributes(AttributeId id, List<Attribute> attrs, AttributeUpdateRecords records) {
        if (records != null) {
            // 需要做更新
        }

        if (attrs.size() == 0) {
            modelAttributeSet.remove(id);
        } else {
            accumulateModelAttribute(id, attrs);
        }
    }

    // 容器加载完成后重新计算
    public void containerRecompute() {
        recompute(null, false);
    }

    // get and set
    public AttributeContainer() {}

    public AttributeContainer(T owner) {
        this.owner = owner;
    }

    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public Map<AttributeType, Attribute> getFinalAttributes() {
        return finalAttributes;
    }

    public void setFinalAttributes(Map<AttributeType, Attribute> finalAttributes) {
        this.finalAttributes = finalAttributes;
    }

    public Map<AttributeId, AttributeSet> getModelAttributeSet() {
        return modelAttributeSet;
    }

    public void setModelAttributeSet(Map<AttributeId, AttributeSet> modelAttributeSet) {
        this.modelAttributeSet = modelAttributeSet;
    }

    public Map<AttributeType, Attribute> getAccumulateAttributes() {
        return accumulateAttributes;
    }

    public void setAccumulateAttributes(Map<AttributeType, Attribute> accumulateAttributes) {
        this.accumulateAttributes = accumulateAttributes;
    }

    // abstract method

    /**
     * 重新计算已变更的属性
     *
     * @param records
     * @param needSyn
     */
    protected abstract void recompute(AttributeUpdateRecords records, boolean needSyn);

}
