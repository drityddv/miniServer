package game.user.equip.recource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.LockAttribute;
import game.user.equip.base.condition.AbstractConditionProcessor;
import game.user.equip.constant.EquipPosition;
import game.user.equip.constant.EquipWearConditionType;
import middleware.anno.Init;
import middleware.anno.MiniResource;
import utils.JodaUtil;

/**
 * @author : ddv
 * @since : 2019/6/28 下午8:23
 */
@MiniResource
public class EquipResource {
    private int configId;
    /**
     * 这个对应的是itemResource中的configId
     */
    private int itemConfigId;

    private List<Attribute> attributes;
    private String attributeString;

    /**
     * 装备的位置信息
     */
    private EquipPosition equipPosition;
    private String equipPositionString;

    private List<AbstractConditionProcessor> conditionProcessors;
    private String conditionString;

    @Init
    public void init() {
        analysisAttrs();
        analysisPosition();
        analysisCondition();
    }

    private void analysisCondition() {
        conditionProcessors = new ArrayList<>();
        String[] attrs = conditionString.split(",");
        for (String attr : attrs) {
            String[] params = attr.split(":");
            Map<String, Integer> paramMap = new HashMap<>();
            Integer value = JodaUtil.convertFromString(Integer.class, params[1]);
            paramMap.put(params[0], value);
            conditionProcessors.add(EquipWearConditionType.getByName(params[0]).createProcessor(paramMap));
        }
    }

    private void analysisPosition() {
        equipPosition = EquipPosition.getPosition(equipPositionString);
    }

    private void analysisAttrs() {
        attributes = new ArrayList<>();
        List<Attribute> temp = new ArrayList<>();
        String[] attrs = attributeString.split(",");
        for (String attr : attrs) {
            String[] params = attr.split(":");
            temp.add(Attribute.valueOf(AttributeType.getByName(params[0]),
                JodaUtil.convertFromString(Integer.class, params[1])));
        }
        for (Attribute attribute : temp) {
            attributes.add(LockAttribute.wrapper(attribute));
        }
    }

    public int getConfigId() {
        return configId;
    }

    public int getItemConfigId() {
        return itemConfigId;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public EquipPosition getEquipPosition() {
        return equipPosition;
    }

    public List<AbstractConditionProcessor> getConditionProcessors() {
        return conditionProcessors;
    }
}
