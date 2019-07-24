package game.base.buff.resource;

import java.util.ArrayList;
import java.util.List;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.BuffTypeEnum;
import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;
import game.base.effect.model.constant.EffectTypeEnum;
import game.base.effect.model.effect.BaseEffect;
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
    private double frequencyTime;
    private int periodTime;
    /**
     * 0 不能merge
     */
    private int maxMergeCount;
    private int level;
    private int groupId;

    private List<EffectTypeEnum> effectTypeEnums;
    private List<BaseEffect> effectList;
    private String effectIdString;

    private BuffContext buffContext;
    // POISON_LEVEL:1&POISON_DAMAGE:10,POISON_LEVEL:2&POISON_DAMAGE:20
    private String effectParamString;
    // merge方式 1 合并 2 新覆盖旧 旧buff调用
    private int mergeType;

    @Init
    private void init() {
        analysisTime();
        analysisEffect();
        analysisEffectContext();
    }

    private void analysisEffectContext() {

        buffContext = BuffContext.valueOf();

        String[] split = effectParamString.split(CsvSymbol.COMMA);
        for (String params : split) {
            String[] paramString = params.split(CsvSymbol.AND);
            for (String param : paramString) {
                String[] singleParam = param.split(CsvSymbol.COLON);
                BuffContextParamEnum contextEnum = BuffContextParamEnum.getByName(singleParam[0]);
                buffContext.addParam(contextEnum,
                    JodaUtil.convertFromString(contextEnum.getParamClazz(), singleParam[1]));
            }
        }
    }

    private void analysisTime() {
        durationTime *= 1000;
        frequencyTime *= 1000;
    }

    private void analysisEffect() {
        effectTypeEnums = new ArrayList<>();
        String[] effectIds = effectIdString.split(CsvSymbol.COMMA);
        for (String effectId : effectIds) {
            effectTypeEnums.add(EffectTypeEnum.getById(Long.parseLong(effectId)));
        }
        effectList = new ArrayList<>();
        effectTypeEnums.forEach(effectTypeEnum -> {
            effectList.add(effectTypeEnum.create());
        });
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

    public List<EffectTypeEnum> getEffectTypeEnums() {
        return effectTypeEnums;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public double getFrequencyTime() {
        return frequencyTime;
    }

    public int getPeriodTime() {
        return periodTime;
    }

    public int getMaxMergeCount() {
        return maxMergeCount;
    }

    public BuffContext getBuffContext() {
        return buffContext;
    }

    public int getGroupId() {
        return groupId;
    }

    public List<BaseEffect> getEffectList() {
        return effectList;
    }

    public int getLevel() {
        return level;
    }
}
