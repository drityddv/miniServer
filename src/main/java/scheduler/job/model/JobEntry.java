package scheduler.job.model;

import static org.quartz.DateBuilder.futureDate;

import org.quartz.*;

/**
 *
 * @author : ddv
 * @since : 2019/7/9 上午12:13
 */

public class JobEntry {

    private JobDetail jobDetail;
    private Trigger trigger;

    public static JobEntry newDelayJob(Class<? extends Job> jobClazz, long delay, long jobId, long groupId) {
        JobEntry entry = new JobEntry();
        String name = jobId + "";
        String groupName = groupId + "";

        JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(name, groupName).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
            .startAt(futureDate((int)delay, DateBuilder.IntervalUnit.MILLISECOND)).forJob(jobDetail).build();

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
}
