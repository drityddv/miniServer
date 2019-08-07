package quartz.job.common.rank;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import spring.SpringContext;

/**
 * rank更新cache的job 每五秒跑一次
 *
 * @author : ddv
 * @since : 2019/8/6 10:13 PM
 */

public class RankUpdateCacheJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringContext.getRankService().updateCache();
    }
}
