package game.base.effect.resource;

import java.util.HashMap;
import java.util.Map;

import game.base.effect.model.constant.EffectTypeEnum;
import resource.anno.Init;
import resource.anno.MiniResource;
import utils.StringUtil;

/**
 * 时间单位配置表中为秒 预处理后为毫秒
 *
 * @author : ddv
 * @since : 2019/7/15 11:09 AM
 */
@MiniResource
public class EffectResource {
    // 配置id
    private long configId;
    // 效果等级
    private int level;
    // buff名称
    private String buffName;
    // 持续时间 表中单位为s
    private long durationTime;
    // 生效次数
    private int periodTime;
    // 生效频率[周期性专享] 表中单位为s
    private double frequencyTime;
    // 最大合并次数 [-1 无限合并,0 不能合并]
    private int maxMergeCount;

    private EffectTypeEnum effectType;
    private long effectTypeId;

    private Map<String, Long> valueParam;
    private String valueString;

    @Init
    private void init() {
        frequencyTime *= 1000;
        if (durationTime == 0) {
            durationTime = (long)(frequencyTime * periodTime);
        }
        analysisValue();
        analysisEffectType();
    }

    private void analysisEffectType() {
        effectType = EffectTypeEnum.getById(effectTypeId);
    }

    private void analysisValue() {
        valueParam = new HashMap<>();
        if (StringUtil.isNotEmpty(valueString)) {
            String[] split = valueString.split(",");
            for (String temp : split) {
                String[] param = temp.split(":");
                valueParam.put(param[0], Long.parseLong(param[1]));

            }
        }
    }

    // 是否是周期性任务
    public boolean isRateEffect() {
        return durationTime != 0 || periodTime > 0;
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

    public int getPeriodTime() {
        return periodTime;
    }

    public int getMaxMergeCount() {
        return maxMergeCount;
    }

    public EffectTypeEnum getEffectType() {
        return effectType;
    }

    public long getFrequencyTime() {
        return (long)frequencyTime;
    }

    public int getLevel() {
        return level;
    }

    public long getEffectTypeId() {
        return effectTypeId;
    }

    public Map<String, Long> getValueParam() {
        return valueParam;
    }
}
