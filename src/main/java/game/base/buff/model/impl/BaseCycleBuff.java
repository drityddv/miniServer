package game.base.buff.model.impl;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.BuffTriggerPoint;
import game.base.buff.resource.BuffResource;
import game.base.effect.model.BuffContext;
import game.world.fight.command.buff.BuffActiveCommand;
import scheduler.constant.JobGroupEnum;
import scheduler.job.common.scene.EffectActiveJob;
import scheduler.job.model.JobEntry;
import spring.SpringContext;

/**
 * 周期调度[延时取消,定时执行,周期执行...]
 *
 * @author : ddv
 * @since : 2019/7/23 8:21 PM
 */

public abstract class BaseCycleBuff extends BaseCreatureBuff {
    // 总共需要执行次数
    protected int periodCount;
    // 剩余执行次数
    protected int remainCount;
    // 执行频率
    protected long frequencyTime;

    @Override
    public void initBuffJob() {
        buffJob = JobEntry.newRateJob(EffectActiveJob.class, frequencyTime, periodCount, buffId,
            JobGroupEnum.BUFF.name(), BuffActiveCommand.valueOf(this, mapId));
        needScheduled = true;
    }

    @Override
    public void forceCancel() {
        cancelSchedule();
        releaseCaster();
        releaseTarget();
    }

    @Override
    public void init(BuffResource buffResource, BuffContext context) {
        super.init(buffResource, context);
        remainCount = periodCount = buffResource.getPeriodTime();
        frequencyTime = (long)buffResource.getFrequencyTime();
        initBuffJob();
    }

    @Override
    public boolean buffActive() {
        boolean success = super.buffActive();
        if (success) {
            schedule();
        }
        return success;
    }

    @Override
    public void triggerBuff(BuffTriggerPoint point) {
        super.triggerBuff(point);
        remainCount--;
    }

    // 默认合并重新生成调度任务
    @Override
    public void merge(BaseCreatureBuff buff) {

    }

    @Override
    public boolean isScheduleBuff() {
        return true;
    }

    @Override
    public void cancelSchedule() {
        SpringContext.getQuartzService().removeJob(buffJob.getJobDetail());
    }

    @Override
    public void tryCancel() {
        if (remainCount == 0) {
            forceCancel();
        }
    }

    public int getPeriodCount() {
        return periodCount;
    }

    public int getRemainCount() {
        return remainCount;
    }

}
