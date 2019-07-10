package game.role.equip.constant;

import java.util.HashMap;
import java.util.Map;

import game.base.condition.AbstractConditionProcessor;
import game.base.condition.PlayerGoldConditionProcessor;
import game.base.condition.PlayerLevelConditionProcessor;
import game.base.message.I18N;
import game.base.message.exception.RequestException;
import utils.ClassUtil;

/**
 * @author : ddv
 * @since : 2019/7/1 下午8:41
 */

public enum EquipWearConditionType {
    /**
     * 等级要求
     */
    LEVEL("等级", PlayerLevelConditionProcessor.class),
    /**
     * 玩家当前金币要求
     */
    GOLD("金币", PlayerGoldConditionProcessor.class),;

    private static Map<String, EquipWearConditionType> NAME_TO_TYPE =
        new HashMap<>(EquipWearConditionType.values().length);

    static {
        for (EquipWearConditionType conditionType : EquipWearConditionType.values()) {
            NAME_TO_TYPE.put(conditionType.name(), conditionType);
        }
    }

    /**
     * 条件描述
     */
    private String conditionType;
    /**
     * 执行器
     */
    private AbstractConditionProcessor processor;
    /**
     * 执行器class
     */
    private Class<? extends AbstractConditionProcessor> processorClass;

    EquipWearConditionType(String conditionType, Class<? extends AbstractConditionProcessor> processorClass) {
        this.conditionType = conditionType;
        this.processorClass = processorClass;
    }

    public static EquipWearConditionType getByName(String typeName) {
        EquipWearConditionType conditionType = NAME_TO_TYPE.get(typeName);
        if (conditionType == null) {
            RequestException.throwException(I18N.ENUM_NULL);
        }
        return conditionType;
    }

    public AbstractConditionProcessor createProcessor(Map conditionParams) {
        return ClassUtil.createProcessor(processorClass, conditionParams, 1);
    }

    public String getConditionType() {
        return conditionType;
    }

    public AbstractConditionProcessor getProcessor() {
        return processor;
    }

}
