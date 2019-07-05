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

    // public void compute(Map<AttributeId, AttributeSet> attributes, Map<AttributeType, Attribute> finalAttributeMap,
    // AttributeUpdateRecords records,Map<AttributeType, Attribute> accumulateAttributes,T owner) {
    //
    // for (Attribute attribute : accumulateAttributes.values()) {
    // attribute.setValue(0);
    // }
    //
    // // 同类型属性相加至计算变量map中
    // AttributeUtils.accumulateToMapWithExclude(attributes, accumulateAttributes, null);
    //
    // List<IAttributeComputer> computers = AttributeType.getComputers();
    //
    // // 如果有修改,需要重新获取这些属性type的计算器
    // if (records != null) {
    // computers = new ArrayList<>(records.getTypes().size());
    // for (AttributeType attributeType : records.getTypes()) {
    // IAttributeComputer computer = attributeType.getAttributeComputer();
    // if (computer != null) {
    // computers.add(computer);
    // }
    // }
    // }
    //
    // for (IAttributeComputer computer : computers) {
    // AttributeType type = computer.getAttributeType();
    // long value = computer.compute(owner, accumulateAttributes, attributes);
    // Attribute finalAttr = finalAttributeMap.get(type);
    //
    // if (finalAttr != null) {
    // finalAttr.setValue(value);
    // } else {
    // finalAttributeMap.put(type, Attribute.valueOf(type, value));
    // }
    //
    // }
    // }
}
