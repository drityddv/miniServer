package scheduler.job.model;

import static org.quartz.DateBuilder.futureDate;

import org.quartz.*;

import spring.SpringContext;

/**
 *
 * @author : ddv
 * @since : 2019/7/9 上午12:13
 */

public class JobEntry {

    private JobDetail jobDetail;
    // 是否已经取消
    private volatile boolean isCanceled = false;
    private Trigger trigger;

    // FIXME 这里int溢出会有问题
    public static JobEntry newDelayJob(Class<? extends Job> jobClazz, long delay, int period, long jobId, long groupId,
        Object jobMapData) {
        JobEntry entry = new JobEntry();
        String name = jobId + "";
        String groupName = groupId + "";

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        jobDetail.getJobDataMap().put("command", jobMapData);

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

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void cancel() {
        SpringContext.getQuartzService().removeJob(jobDetail);
        isCanceled = true;
    }
}