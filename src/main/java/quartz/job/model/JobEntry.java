package quartz.job.model;

import static org.quartz.DateBuilder.futureDate;

import java.util.Date;

import org.quartz.*;

import game.base.executor.command.constant.ExecutorConstant;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.world.base.command.scene.ReliveCommand;
import quartz.constant.JobGroupEnum;
import quartz.constant.ScheduleConstant;
import quartz.job.common.scene.SceneCommandJob;
import quartz.job.common.scene.SceneReliveJob;
import spring.SpringContext;
import utils.snow.IdUtil;

/**
 *
 * @author : ddv
 * @since : 2019/7/9 上午12:13
 */

public class JobEntry {

    private JobDetail jobDetail;
    private Trigger trigger;

    public static JobEntry newMapObjectReliveJob(long delay, long unitId, int mapId, long sceneId) {
        ReliveCommand command = ReliveCommand.valueOf(mapId, unitId, sceneId);
        return newDelayJob(SceneReliveJob.class, delay, unitId, JobGroupEnum.SCENE_RELIVE.name(), command);
    }

    public static JobEntry newSceneDelayJob(long delay, AbstractSceneCommand command) {
        return newDelayJob(SceneCommandJob.class, delay, IdUtil.getLongId(), JobGroupEnum.SCENE_COMMAND.name(),
            command);
    }

    public static JobEntry newSceneRateJob(long delay, int period, AbstractSceneCommand command) {
        return newRateJob(SceneCommandJob.class, delay, period, IdUtil.getLongId(),
            JobGroupEnum.SCENE_COMMON_RATE.name(), command);
    }

    public static JobEntry newDelayJob(Class<? extends Job> jobClazz, long delay, long jobId, String groupName,
        Object jobMapData) {
        JobEntry entry = new JobEntry();
        String name = jobId + "";

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        jobDetail.getJobDataMap().put(ExecutorConstant.COMMAND, jobMapData);

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
        jobDetail.getJobDataMap().put(ExecutorConstant.COMMAND, jobMapData);

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
        jobDetail.getJobDataMap().put(ExecutorConstant.COMMAND, command);

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
            .startAt(futureDate((int)delay, DateBuilder.IntervalUnit.MILLISECOND)).forJob(jobDetail).build();

        entry.jobDetail = jobDetail;
        entry.trigger = trigger;
        return entry;
    }

    // not running when submit job
    public static JobEntry newRateJob(Class<? extends Job> jobClazz, long delay, long period, long jobId,
        String groupName, Object jobMapData) {
        JobEntry entry = new JobEntry();
        String name = jobId + "";
        Trigger trigger;
        Date startAt = new Date();
        startAt.setTime(startAt.getTime() + delay);

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        jobDetail.getJobDataMap().put(ExecutorConstant.COMMAND, jobMapData);

        if (period == 0) {
            trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMilliseconds(delay))
                .forJob(jobDetail).build();
        } else {
            trigger = TriggerBuilder
                .newTrigger().withIdentity(name, groupName).withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withRepeatCount((int)(period - 1)).withIntervalInMilliseconds(delay))
                .startAt(startAt).forJob(jobDetail).build();
        }

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

    public void cancel() {
        SpringContext.getQuartzService().removeJob(jobDetail);
    }
}
