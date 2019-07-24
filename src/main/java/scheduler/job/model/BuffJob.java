package scheduler.job.model;

import game.base.buff.model.BaseCreatureBuff;
import utils.snow.IdUtil;

/**
 * @author : ddv
 * @since : 2019/7/23 5:35 PM
 */

public class BuffJob {
    /**
     * 作业id
     */
    private long jobId;
    /**
     * buff
     */
    private BaseCreatureBuff buff;
    /**
     * 作业内容
     */
    private JobEntry jobEntry;

    public BuffJob(BaseCreatureBuff buff, JobEntry jobEntry) {
        this.jobId = IdUtil.getLongId();
        this.buff = buff;
        this.jobEntry = jobEntry;
    }

    public static BuffJob valueOf(BaseCreatureBuff buff, JobEntry jobEntry) {
        BuffJob job = new BuffJob(buff, jobEntry);
        return job;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public BaseCreatureBuff getBuff() {
        return buff;
    }

    public void setBuff(BaseCreatureBuff buff) {
        this.buff = buff;
    }

    public JobEntry getJobEntry() {
        return jobEntry;
    }

    public void setJobEntry(JobEntry jobEntry) {
        this.jobEntry = jobEntry;
    }
}
