package game.base.buff.resource;

import java.util.*;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.BuffParamEnum;
import game.base.buff.model.BuffTriggerPoint;
import game.base.buff.model.BuffTypeEnum;
import game.base.buff.model.BaseBuffConfig;
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

    private Map<BuffTriggerPoint, List<BaseEffect>> triggerPoints;
    private String effectIdString;

    private BaseBuffConfig buffConfig;
    // POISON_LEVEL:1&POISON_DAMAGE:10,POISON_LEVEL:2&POISON_DAMAGE:20
    private String effectParamString;
    // merge方式 1 合并 2 新覆盖旧 旧buff调用
    private int mergeType;

    @Init
    private void init() {
        analysisTriggerPoints();
        analysisEffectContext();
    }

    private void analysisTriggerPoints() {
        triggerPoints = new HashMap<>();
        for (BuffTriggerPoint triggerPoint : BuffTriggerPoint.values()) {
            triggerPoints.put(triggerPoint, new ArrayList<>());
        }

        String[] effectIds = effectIdString.split(CsvSymbol.Comma);
        for (String effectId : effectIds) {
            BaseEffect baseEffect = EffectTypeEnum.getById(Long.parseLong(effectId)).create();
            Set<BuffTriggerPoint> triggerPointSet =
                EffectTypeEnum.getByClazz(baseEffect.getClass()).getTriggerPointSet();
            triggerPointSet.forEach(buffTriggerPoint -> {
                triggerPoints.get(buffTriggerPoint).add(baseEffect);
            });
        }

    }

    private void analysisEffectContext() {
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

    public Map<BuffTriggerPoint, List<BaseEffect>> getTriggerPoints() {
        return triggerPoints;
    }

    public BaseBuffConfig getBuffConfig() {
        return buffConfig;
    }
}
