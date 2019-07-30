package game.base.buff.model.impl;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.BuffContext;
import game.base.buff.model.BuffTriggerPointEnum;
import game.base.buff.resource.BuffResource;
import game.world.fight.command.buff.BuffActiveCommand;
import game.world.fight.command.buff.BuffCancelCommand;
import quartz.constant.JobGroupEnum;
import quartz.job.common.scene.BuffCancelDelayJob;
import quartz.job.common.scene.EffectActiveJob;
import quartz.job.model.JobEntry;
import spring.SpringContext;

/**
 * 周期调度[延时取消,定时执行,周期执行...]Buff
 *
 * @author : ddv
 * @since : 2019/7/23 8:21 PM
 */

public abstract class BaseScheduleBuff extends BaseCreatureBuff {
    // 总共需要执行次数
    protected int periodCount;
    // 剩余执行次数
    protected int remainCount;
    // 执行频率
    protected long frequencyTime;
    // 延迟关闭时间
    protected long delayEndTime;

    @Override
    public void initBuffJob() {
        if (isScheduleBuff()) {
            scheduleJob = JobEntry.newBuffRateJob(EffectActiveJob.class, frequencyTime, periodCount, buffId,
                JobGroupEnum.BUFF.name(), BuffActiveCommand.valueOf(this, mapId));
            needScheduled = true;
        }

        if (isNeedCancel()) {
            cancelJob = JobEntry.newBuffCancelJob(BuffCancelDelayJob.class, delayEndTime, buffId,
                JobGroupEnum.BUFF.name(), BuffCancelCommand.valueOf(this, mapId));
            needScheduleCancel = true;
        }

    }

    @Override
    public void forceCancel() {
        cancelSchedule();
        releaseCaster();
        releaseTarget();
        triggerBuff(BuffTriggerPointEnum.End);
    }

    @Override
    public void init(BuffResource buffResource, BuffContext context) {
        super.init(buffResource, context);
        remainCount = periodCount = buffResource.getPeriodCount();
        frequencyTime = (long)buffResource.getFrequencyTime();
        delayEndTime = buffResource.getDurationTime();
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
    public void triggerBuff(BuffTriggerPointEnum point) {
        if (point == BuffTriggerPointEnum.Schedule_Active) {
            remainCount--;
        }
        super.triggerBuff(point);
    }

    // 默认合并重新生成调度任务
    @Override
    public void merge(BaseCreatureBuff buff) {
        if (buff instanceof BaseScheduleBuff) {
            BaseScheduleBuff newBuff = (BaseScheduleBuff)buff;
            this.mergedCount++;
            this.delayEndTime = remainCount * frequencyTime + newBuff.getDelayEndTime();
            this.remainCount += newBuff.remainCount;
            this.periodCount = remainCount;
            this.frequencyTime = Math.min(this.frequencyTime, newBuff.frequencyTime);
            this.needScheduled = true;
            this.needScheduleCancel = true;
        }
    }

    @Override
    public boolean isScheduleBuff() {
        return true;
    }

    @Override
    public void cancelSchedule() {
        SpringContext.getQuartzService().removeJob(scheduleJob.getJobDetail());
        SpringContext.getQuartzService().removeJob(cancelJob.getJobDetail());
    }

    @Override
    public void tryCancel() {
        forceCancel();
    }

    public int getPeriodCount() {
        return periodCount;
    }

    public int getRemainCount() {
        return remainCount;
    }

    public long getFrequencyTime() {
        return frequencyTime;
    }

    public long getDelayEndTime() {
        return delayEndTime;
    }
}
