package game.base.effect.model;

import java.util.List;

import game.base.effect.model.constant.EffectMergeConstant;
import game.base.effect.resource.EffectResource;
import utils.TimeUtil;
import utils.snow.IdUtil;

/**
 * 基础效果[支持合并,过期]
 *
 * @author : ddv
 * @since : 2019/7/15 10:39 AM
 */

public abstract class BaseEffect<T> {
    // 作业id
    protected long jobId;
    // buff作用对象
    protected List<T> targetList;
    // 开始时间
    protected long startAt;
    // 持续时间
    protected long duration;
    // 当前合并次数
    protected long mergedCount;

    protected EffectResource effectResource;

    public void init(List<T> ownerList, EffectResource effectResource) {
        this.jobId = IdUtil.getLongId();
        this.targetList = ownerList;
        this.effectResource = effectResource;
        this.startAt = TimeUtil.now();
        this.duration = effectResource.getDurationTime();
    }

    /**
     * 效果合并
     *
     * @param buffEffect
     */
    public abstract void merge(BaseBuffEffect buffEffect);

    public EffectResource getEffectResource() {
        return effectResource;
    }

    public Long getConfigId() {
        return effectResource.getConfigId();
    }

    public int getLevel() {
        return effectResource.getLevel();
    }

    public long getJobId() {
        return jobId;
    }

    public boolean canMerge() {
        return effectResource.getMaxMergeCount() != EffectMergeConstant.CAN_NOT_MERGE;
    }

    public long getEffectTypeId() {
        return effectResource.getEffectTypeId();
    }

}
