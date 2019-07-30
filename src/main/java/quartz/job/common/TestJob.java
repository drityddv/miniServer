package quartz.job.common;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author : ddv
 * @since : 2019/7/17 4:44 PM
 */

public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String name = context.getJobDetail().getKey().getName();
        System.out.println("i am running" + " " + name);
    }
}
