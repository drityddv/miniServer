package game.role.equip.recource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.condition.AbstractConditionProcessor;
import game.base.game.attribute.Attribute;
import game.role.equip.constant.EquipPosition;
import game.role.equip.constant.EquipWearConditionType;
import middleware.anno.Init;
import middleware.anno.MiniResource;
import utils.JodaUtil;
import utils.ResourceUtil;

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
        attributes = ResourceUtil.initAttrs(attributeString);
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
