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
     * FIXME 这个拥有者会被player引用了 不要序列化 所以外界不要调用字段
     */
    protected transient T owner;
    /**
     * 当前属性
     */
    protected transient Map<AttributeType, Attribute> finalAttributes = new ConcurrentHashMap<>();
    /**
     * 模块属性[基本属性,传世,雷霆等...]
     */
    protected transient Map<AttributeId, AttributeSet> modelAttributeSet = new HashMap<>();
    /**
     * 计算使用的变量
     */
    protected transient Map<AttributeType, Attribute> accumulateAttributes = new HashMap<>();

    // get and set
    public AttributeContainer() {}

    public AttributeContainer(T owner) {
        this.owner = owner;
    }

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
     * 模块属性变动 调用这个方法 如果要删除某个模块 不要调用这里
     *
     * @param id
     * @param attrs
     * @param needSync
     */
    public void putAttributesWithRecompute(AttributeId id, List<Attribute> attrs, boolean needSync) {
        AttributeUpdateRecords records = new AttributeUpdateRecords(id);
        // 这里的record传递给模块各自的计算方法 不需要全部遍历一遍去计算
        if (modelAttributeSet.containsKey(id) || (attrs != null && attrs.size() > 0)) {
            putAttributes(id, attrs, records);
            recompute(records, needSync);
        }

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
            AttributeSet oldAttrs = modelAttributeSet.get(id);
            records.addAttrs(attrs);

            if (oldAttrs != null) {
                records.addAttrs(oldAttrs.getAttributeMap().values());
                records.setRemovedAttributes(oldAttrs.getAttributeMap().values());
            }
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

    public abstract void clear();
}
