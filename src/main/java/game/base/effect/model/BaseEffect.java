package game.base.effect.model;

import game.base.effect.model.constant.EffectMergeConstant;
import game.base.effect.resource.EffectResource;
import utils.TimeUtil;

/**
 * 基础效果[支持合并,过期]
 *
 * @author : ddv
 * @since : 2019/7/15 10:39 AM
 */

public abstract class BaseEffect<T> {
    // 作业id
    protected long jobId;
    // buff作用者
    protected T owner;
    // 开始时间
    protected long startAt;
    // 持续时间
    protected long duration;
    // 结束时间
    protected long endAt;
    // 当前合并次数
    protected long mergedCount;

    protected EffectResource effectResource;

    public void init(T owner, EffectResource effectResource) {
        this.owner = owner;
        this.effectResource = effectResource;
        this.startAt = TimeUtil.now();
        this.duration = effectResource.getDuration();
        this.endAt = startAt + duration;
    }

    /**
     * 效果合并
     *
     * @param buffEffect
     */
    public abstract void merge(BaseBuffEffect buffEffect);

    // get and set
    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getEndAt() {
        return endAt;
    }

    public void setEndAt(long endAt) {
        this.endAt = endAt;
    }

    public long getMergedCount() {
        return mergedCount;
    }

    public void setMergedCount(long mergedCount) {
        this.mergedCount = mergedCount;
    }

    public EffectResource getEffectResource() {
        return effectResource;
    }

    public void setEffectResource(EffectResource effectResource) {
        this.effectResource = effectResource;
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

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public boolean canMerge() {
        return effectResource.getMaxMergeCount() != EffectMergeConstant.CAN_NOT_MERGE;
    }

    public int getGroupId() {
        return effectResource.getGroupId();
    }
}
