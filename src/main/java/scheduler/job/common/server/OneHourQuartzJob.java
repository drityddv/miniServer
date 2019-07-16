package scheduler.job.common.server;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import spring.SpringContext;

/**
 * 整点业务
 *
 * @author : ddv
 * @since : 2019/7/9 下午12:26
 */

public class OneHourQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringContext.getCommonService().oneHourJob();
    }

}
