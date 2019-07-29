package game.base.buff.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.buff.model.*;
import game.base.effect.model.BaseEffect;
import game.base.effect.model.constant.EffectTypeEnum;
import resource.anno.Init;
import resource.anno.MiniResource;
import resource.constant.CsvSymbol;
import utils.JodaUtil;

/**
 * @author : ddv
 * @since : 2019/7/24 10:34 AM
 */
@MiniResource
public class BuffResource {
    private long configId;
    private String buffName;
    private int buffTypeId;
    private long durationTime;
    private long frequencyTime;
    private int periodCount;
    /**
     * 0 不能merge
     */
    private int maxMergeCount;
    private int level;
    private int groupId;

    private Map<BuffTriggerPointEnum, List<BaseEffect>> triggerPoints;
    // First_Active:2&3,Schedule_Active:2&1
    private String triggerPointString;

    private BaseBuffConfig buffConfig;
    // POISON_LEVEL:1&POISON_DAMAGE:10,POISON_LEVEL:2&POISON_DAMAGE:20
    private String effectParamString;

    @Init
    private void init() {
        analysisTriggerPoints();
        analysisBuffConfig();
    }

    private void analysisTriggerPoints() {
        triggerPoints = new HashMap<>();
        for (BuffTriggerPointEnum triggerPoint : BuffTriggerPointEnum.values()) {
            triggerPoints.put(triggerPoint, new ArrayList<>());
        }

        String[] split = triggerPointString.split(CsvSymbol.Comma);
        for (String pointString : split) {
            String[] strings = pointString.split(CsvSymbol.Colon);
            String[] pointIds = strings[1].split(CsvSymbol.AND);
            BuffTriggerPointEnum triggerPointEnum = BuffTriggerPointEnum.getByName(strings[0]);
            for (String pointId : pointIds) {
                triggerPoints.get(triggerPointEnum).add(EffectTypeEnum.getById(Long.parseLong(pointId)).create());
            }
        }
    }

    private void analysisBuffConfig() {
        buffConfig = BaseBuffConfig.valueOf();
        String[] split = effectParamString.split(CsvSymbol.Comma);
        for (String params : split) {
            String[] paramString = params.split(CsvSymbol.AND);
            for (String param : paramString) {
                String[] singleParam = param.split(CsvSymbol.Colon);
                BuffParamEnum contextEnum = BuffParamEnum.getByName(singleParam[0]);
                buffConfig.addParam(contextEnum,
                    JodaUtil.convertFromString(contextEnum.getParamClazz(), singleParam[1]));
            }
        }
    }

    public BaseCreatureBuff createBuff() {
        BaseCreatureBuff buff = BuffTypeEnum.getById(buffTypeId).create();
        return buff;
    }

    public int getBuffTypeId() {
        return buffTypeId;
    }

    public long getConfigId() {
        return configId;
    }

    public String getBuffName() {
        return buffName;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public double getFrequencyTime() {
        return frequencyTime;
    }

    public int getPeriodCount() {
        return periodCount;
    }

    public int getMaxMergeCount() {
        return maxMergeCount;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getLevel() {
        return level;
    }

    public Map<BuffTriggerPointEnum, List<BaseEffect>> getTriggerPoints() {
        return triggerPoints;
    }

    public BaseBuffConfig getBuffConfig() {
        return buffConfig;
    }

}
