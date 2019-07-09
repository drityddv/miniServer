package middleware.sehedule.job.common;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 公共定时
 *
 * @author : ddv
 * @since : 2019/7/9 下午12:26
 */

public class CommonQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
