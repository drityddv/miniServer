package scheduler.job.model;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * 定时对象
 *
 * @author : ddv
 * @since : 2019/7/9 上午12:13
 */

public class JobEntity {

    private JobDetail jobDetail;
    private Trigger trigger;

    public JobEntity(JobDetail jobDetail, Trigger trigger) {
        this.jobDetail = jobDetail;
        this.trigger = trigger;
    }

    public static JobEntity valueOf(JobDetail jobDetail, Trigger trigger) {
        return new JobEntity(jobDetail, trigger);
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
