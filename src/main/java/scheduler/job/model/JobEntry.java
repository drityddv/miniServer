package scheduler.job.model;

import static org.quartz.DateBuilder.futureDate;

import org.quartz.*;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.world.base.command.scene.ReliveCommand;
import scheduler.constant.JobGroupEnum;
import scheduler.constant.ScheduleConstant;
import scheduler.job.common.scene.SceneReliveJob;
import spring.SpringContext;

/**
 *
 * @author : ddv
 * @since : 2019/7/9 上午12:13
 */

public class JobEntry {

    private JobDetail jobDetail;
    private Trigger trigger;

    public static JobEntry newMapObjectReliveJob(long delay, long unitId, int mapId) {
        ReliveCommand command = ReliveCommand.valueOf(mapId, unitId);
        return newDelayJob(SceneReliveJob.class, delay, unitId, JobGroupEnum.SCENE_RELIVE.name(), command);
    }

    public static JobEntry newDelayJob(Class<? extends Job> jobClazz, long delay, long jobId, String groupName,
        Object jobMapData) {
        JobEntry entry = new JobEntry();
        String name = jobId + "";

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        jobDetail.getJobDataMap().put("command", jobMapData);

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
            .startAt(futureDate((int)delay, DateBuilder.IntervalUnit.MILLISECOND)).forJob(jobDetail).build();

        entry.jobDetail = jobDetail;
        entry.trigger = trigger;
        return entry;
    }

    // buff周期调度
    public static JobEntry newBuffRateJob(Class<? extends Job> jobClazz, long delay, long period, long jobId,
        String groupName, AbstractSceneCommand jobMapData) {
        JobEntry entry = new JobEntry();
        String name = jobId + ScheduleConstant.SCHEDULE_NAME;

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        jobDetail.getJobDataMap().put("command", jobMapData);

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName).withSchedule(
            SimpleScheduleBuilder.simpleSchedule().withRepeatCount((int)(period - 1)).withIntervalInMilliseconds(delay))
            .forJob(jobDetail).build();

        entry.jobDetail = jobDetail;
        entry.trigger = trigger;
        return entry;
    }

    // buff延迟取消
    public static JobEntry newBuffCancelJob(Class<? extends Job> jobClazz, long delay, long jobId, String groupName,
        AbstractSceneCommand command) {
        JobEntry entry = new JobEntry();
        String name = jobId + ScheduleConstant.CANCEL_NAME;

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        jobDetail.getJobDataMap().put("command", command);

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
            .startAt(futureDate((int)delay, DateBuilder.IntervalUnit.MILLISECOND)).forJob(jobDetail).build();

        entry.jobDetail = jobDetail;
        entry.trigger = trigger;
        return entry;
    }

    public static JobEntry newRateJob(Class<? extends Job> jobClazz, long delay, long period, long jobId,
        String groupName, Object jobMapData) {
        JobEntry entry = new JobEntry();
        String name = jobId + "";

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        jobDetail.getJobDataMap().put("command", jobMapData);

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName).withSchedule(
            SimpleScheduleBuilder.simpleSchedule().withRepeatCount((int)(period - 1)).withIntervalInMilliseconds(delay))
            .forJob(jobDetail).build();

        entry.jobDetail = jobDetail;
        entry.trigger = trigger;
        return entry;
    }

    public void schedule() {
        SpringContext.getQuartzService().addJob(jobDetail, trigger);
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
