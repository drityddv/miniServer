package game.role.equip.recource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.consume.AssetsConsume;
import game.base.consume.IConsume;
import game.base.consumer.AbstractConsumeProcessor;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.util.AttributeUtils;
import game.role.equip.constant.EquipConsumeType;
import game.role.equip.constant.EquipPosition;
import resource.anno.Init;
import resource.anno.MiniResource;
import utils.JodaUtil;
import utils.ResourceUtil;
import utils.StringUtil;

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

    /**
     * 新版消耗配置
     */
    private List<IConsume> consumeList;
    private String newConsumeString;

    @Init
    public void init() {
        equipPosition = EquipPosition.getPosition(equipPositionString);

        analysisAttrs();
        analysisConsumers();
        analysisNewConsumers();
    }

    private void analysisNewConsumers() {
        consumeList = new ArrayList<>();
        if (StringUtil.isNotEmpty(newConsumeString)) {
            String[] split = newConsumeString.split(",");
            for (String value : split) {
                AssetsConsume consume = new AssetsConsume();
                consume.doParse(value);
                consumeList.add(consume);
            }
        }
    }

    private void analysisAttrs() {
        attributes = ResourceUtil.initAttrs(attributeString);
        attributeMap = new HashMap<>();
        AttributeUtils.accumulateToMap(attributes, attributeMap);
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

    public List<IConsume> getConsumeList() {
        return consumeList;
    }
}
