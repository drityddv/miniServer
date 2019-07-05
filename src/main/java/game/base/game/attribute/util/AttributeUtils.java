package game.base.game.attribute.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.game.attribute.*;
import game.base.game.attribute.computer.IAttributeComputer;
import game.base.game.attribute.id.AttributeId;
import game.base.object.AbstractCreature;
import utils.StringUtil;

/**
 *
 * 计算逻辑 每个AttributeId模块内的属性相加合并后再计算百分比加成
 *
 * @author : ddv
 * @since : 2019/6/27 上午10:14
 */

public class AttributeUtils {

    /**
     * 获取attributeMap中所有属性值总和,每种属性会再次计算需要累加上去的属性集合总值：物理攻击上限=[物理攻击+物理攻击上限]
     *
     * @param attributeMap
     * @param type
     * @return
     */
    public static long getCalculateAttributeValue(Map<AttributeType, Attribute> attributeMap, AttributeType type) {
        long attributeValue = getRealCalculateAttributeValue(attributeMap, type);
        return attributeValue > 0 ? attributeValue : 0;
    }

    // pvp可能会用到 暂时先private
    private static long getRealCalculateAttributeValue(Map<AttributeType, Attribute> attributeMap, AttributeType type) {
        long attributeValue = 0;
        for (AttributeType attributeType : type.getCalculateAttrs()) {
            if (attributeMap.get(attributeType) != null) {
                attributeValue += attributeMap.get(attributeType).getValue();
            }
        }
        return attributeValue;
    }

    /**
     * 获取对attributeMap中属性进行加成的属性[仅限于传进来的map中的属性]百分比之和
     *
     * 例如:物理攻击下限-[攻击加成,物理攻击加成,物理攻击下限比例]
     *
     * @param attributeMap
     * @param type
     * @return
     */
    public static long getCalculateAttributeRate(Map<AttributeType, Attribute> attributeMap, AttributeType type) {
        long attributeValue = 0;

        if (type.getCalculateRateAttributes() != null) {
            for (AttributeType attributeType : type.getCalculateRateAttributes()) {

                Attribute attribute = attributeMap.get(attributeType);

                if (attribute != null) {
                    attributeValue += attribute.getValue();
                }
            }
        }

        return attributeValue;
    }

    /**
     * 将属性统计进给定的参数map中
     *
     * @param attributeList
     * @param resultMap
     */
    public static void accumulateToMap(Collection<Attribute> attributeList, Map<AttributeType, Attribute> resultMap) {
        if (attributeList == null) {
            return;
        }
        for (Attribute attribute : attributeList) {
            Attribute attr = resultMap.get(attribute.getAttributeType());
            if (attr == null) {
                resultMap.put(attribute.getAttributeType(),
                    Attribute.valueOf(attribute.getAttributeType(), attribute.getValue()));
            } else {
                attr.alter(attribute.getValue());
            }
        }
    }

    /**
     * 将attributeMap属性累加到result中
     *
     * @param attributeMap
     * @param result
     */
    public static void accumulateToMap(Map<AttributeType, Attribute> attributeMap,
        Map<AttributeType, Attribute> result) {
        for (Attribute add : attributeMap.values()) {
            if (add.getValue() == 0) {
                continue;
            }

            Attribute attribute = result.get(add.getAttributeType());
            if (attribute != null) {
                attribute.alter(add.getValue());
            } else {
                result.put(add.getAttributeType(), Attribute.valueOf(add.getAttributeType(), add.getValue()));
            }
        }
    }

    /**
     * 将模块属性统计进给定的map中
     *
     * @param attributeSet
     * @param result
     */
    public static void accumulateToMap(AttributeSet attributeSet, Map<AttributeType, Attribute> result) {
        if (attributeSet != null) {
            accumulateToMap(attributeSet.getAttributeMap(), result);
        }
    }

    /**
     * 把模块属性统计进list中
     *
     * @param attributeSet
     * @param result
     */
    public static void accumulateToMap(AttributeSet attributeSet, List<Attribute> result) {
        attributeSet.getAttributeMap().forEach((type, attribute) -> {
            result.add(attribute);
        });
    }

    /**
     * 将模块属性统计进给定的List中
     *
     * @param finalAttrs
     * @param attributes
     */
    public static void accumulateToList(Map<AttributeType, Attribute> finalAttrs, List<Attribute> attributes) {
        for (Attribute attribute : finalAttrs.values()) {
            attributes.add(attribute);
        }
    }

    /**
     * 将来pvp之类的也会调用这个方法 所以抽出来
     *
     * @param attributeMap
     * @param type
     * @return
     */
    public static long getAttributeValue(Map<AttributeType, Attribute> attributeMap, AttributeType type) {
        Attribute attribute = attributeMap.get(type);
        if (attributeMap == null || attribute == null) {
            return 0;
        }
        return attribute.getValue() < 0 ? 0 : attribute.getValue();
    }

    /**
     * 获取属性真实值
     *
     * @param attributeMap
     * @param type
     * @return
     */
    public static long getRealAttributeValue(Map<AttributeType, Attribute> attributeMap, AttributeType type) {
        if (attributeMap == null) {
            return 0;
        }
        Attribute attribute = attributeMap.get(type);
        return attribute == null ? 0 : attribute.getValue();
    }

    /**
     * 将模块属性按类型累加起来
     *
     * @param target
     * @param result
     * @param excludeId
     */
    public static void accumulateToMapWithExclude(Map<AttributeId, AttributeSet> target,
        Map<AttributeType, Attribute> result, List<AttributeId> excludeId) {
        for (Map.Entry<AttributeId, AttributeSet> entry : target.entrySet()) {
            if (excludeId != null && excludeId.contains(entry.getKey())) {
                continue;
            }
            accumulateToMap(entry.getValue(), result);
        }
    }

    public static void compute(Map<AttributeId, AttributeSet> attributes,
        Map<AttributeType, Attribute> finalAttributeMap, AttributeUpdateRecords records,
        Map<AttributeType, Attribute> accumulateAttributes, Object owner) {

        for (Attribute attribute : accumulateAttributes.values()) {
            attribute.setValue(0);
        }

        // 同类型属性相加至计算变量map中
        AttributeUtils.accumulateToMapWithExclude(attributes, accumulateAttributes, null);

        List<IAttributeComputer> computers = AttributeType.getComputers();

        // 如果有修改,需要重新获取这些属性type的计算器
        if (records != null) {
            computers = new ArrayList<>(records.getTypes().size());
            for (AttributeType attributeType : records.getTypes()) {
                IAttributeComputer computer = attributeType.getAttributeComputer();
                if (computer != null) {
                    computers.add(computer);
                }
            }
        }

        for (IAttributeComputer computer : computers) {
            AttributeType type = computer.getAttributeType();
            long value = 0;
            if (owner instanceof AbstractCreature) {
                value = computer.compute((AbstractCreature)owner, accumulateAttributes, attributes);
            } else if (owner instanceof BaseCreatureUnit) {
                value = computer.computeFinalForPVP((BaseCreatureUnit)owner, accumulateAttributes, attributes);
            }
            Attribute finalAttr = finalAttributeMap.get(type);

            if (finalAttr != null) {
                finalAttr.setValue(value);
            } else {
                finalAttributeMap.put(type, Attribute.valueOf(type, value));
            }

        }
    }

    public static void logAttrs(AttributeContainer attributeContainer, StringBuilder sb) {
        Map<AttributeId, AttributeSet> modelAttributeSet = attributeContainer.getModelAttributeSet();
        modelAttributeSet.forEach((attributeId, attributeSet) -> {
            sb.append(StringUtil.wipePlaceholder("模块名[{}]", attributeId.getName()));
            Map<AttributeType, Attribute> attributeMap = attributeSet.getAttributeMap();
            attributeMap.forEach((type, attribute) -> {
                sb.append(
                    "   " + StringUtil.wipePlaceholder("属性类型[{}],属性值[{}]", type.getTypeName(), attribute.getValue()));
            });
        });

        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();

        sb.append(StringUtil.wipePlaceholder("[最终属性]") + '\n');
        finalAttributes.forEach((type, attribute) -> {
            sb.append("   " + StringUtil.wipePlaceholder("属性类型[{}],属性值[{}]", type.getTypeName(), attribute.getValue()));
        });
    }

}
