package game.base.effect.resource;

import java.util.HashMap;
import java.util.Map;

import resource.anno.Init;
import resource.anno.MiniResource;

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
    // 效果组[例如mk的锤子有五个等级 都是1组的]
    private int groupId;
    // 效果等级
    private int level;
    // buff名称
    private String buffName;
    // 持续时间
    private long duration;
    // 生效周期
    private long periodTime;
    // 最大合并次数 [-1 无限合并,0 不能合并]
    private int maxMergeCount;

    // private EffectType effectType;

    private Map<String, Object> valueParam;
    private String valueString;

    @Init
    private void init() {
        duration *= 1000;
        periodTime *= 1000;
        analysisValue();
    }

    private void analysisValue() {
        valueParam = new HashMap<>();
        if (utils.StringUtil.isNotEmpty(valueString)) {
            if (!valueString.isEmpty()) {
                String[] split = valueString.split(",");
                for (String temp : split) {
                    String[] param = temp.split(":");
                    valueParam.put(param[0], param[1]);
                }
            }
        }
    }

    public long getConfigId() {
        return configId;
    }

    public String getBuffName() {
        return buffName;
    }

    public long getDuration() {
        return duration;
    }

    public long getPeriodTime() {
        return periodTime;
    }

    public int getMaxMergeCount() {
        return maxMergeCount;
    }

    // public EffectType getEffectType() {
    // return effectType;
    // }

    public int getGroupId() {
        return groupId;
    }

    public int getLevel() {
        return level;
    }
}
