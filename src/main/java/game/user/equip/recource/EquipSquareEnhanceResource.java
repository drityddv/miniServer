package game.user.equip.recource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.LockAttribute;
import game.user.equip.base.consumer.AbstractConsumeProcessor;
import game.user.equip.constant.EquipConsumeType;
import game.user.equip.constant.EquipPosition;
import middleware.anno.Init;
import middleware.anno.MiniResource;
import utils.JodaUtil;

/**
 * @author : ddv
 * @since : 2019/6/28 下午2:23
 */
@MiniResource
public class EquipSquareEnhanceResource {

    private long configId;
    /**
     * 下一等级的配置id 无则为0
     */
    private int nextLevelConfigId;
    // 当前等级
    private int level;
    // 下一等级 无则为0
    private int nextLevel;
    /**
     * 位置
     */
    private EquipPosition equipPosition;
    private String equipPositionString;
    /**
     * 属性 实际存放的是LockAttribute
     */
    private List<Attribute> attributes;
    private Map<AttributeType, Attribute> attributeMap;
    private String attributeString;
    /**
     * 强化消耗
     */
    private List<AbstractConsumeProcessor> processors;
    private String consumeString;

    @Init
    public void init() {
        equipPosition = EquipPosition.getPosition(equipPositionString);
        analysisAttrs();
        analysisConsumers();
    }

    private void analysisAttrs() {
        String[] attrs = attributeString.split(",");
        attributes = new ArrayList<>();
        attributeMap = new HashMap<>();
        for (String attr : attrs) {
            String[] params = attr.split(":");
            attributes.add(LockAttribute.wrapper(Attribute.valueOf(AttributeType.getByName(params[0]),
                JodaUtil.convertFromString(Integer.class, params[1]))));
        }

        for (Attribute attribute : attributes) {
            attributeMap.put(attribute.getAttributeType(), attribute);
        }
    }

    private void analysisConsumers() {
        processors = new ArrayList<>();
        String[] consumers = consumeString.split(",");
        for (String consumer : consumers) {
            Map<Object, Object> paramsMap = new HashMap<>();
            String[] params = consumer.split(":");
            EquipConsumeType consumeType = EquipConsumeType.getConsumer(params[0]);
            paramsMap.put(JodaUtil.convertFromString(Long.class, params[1]),
                JodaUtil.convertFromString(Integer.class, params[2]));

            processors.add(consumeType.createProcessor(paramsMap));
        }
    }

    public long getConfigId() {
        return configId;
    }

    public int getLevel() {
        return level;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public EquipPosition getEquipPosition() {
        return equipPosition;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

	public Map<AttributeType, Attribute> getAttributeMap() {
		return attributeMap;
	}

	public List<AbstractConsumeProcessor> getProcessors() {
        return processors;
    }

    public int getNextLevelConfigId() {
        return nextLevelConfigId;
    }
}
