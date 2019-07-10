package scheduler;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import scheduler.constant.JobGroupEnum;
import scheduler.job.common.CronConst;
import scheduler.job.common.OneHourQuartzJob;
import utils.snow.IdUtil;

/**
 * 定时调度服务
 *
 * @author : ddv
 * @since : 2019/7/8 下午11:05
 */
@Component
public class QuartzService {
    private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);

    private Scheduler scheduler;

    public void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            initOneHourJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 整点任务
    private void initOneHourJob() {
        String groupName = JobGroupEnum.PUBLIC_COMMON.name();
        String jobName = IdUtil.getLongId() + "";
        String triggerName = IdUtil.getLongId() + "";

        JobDetail jobDetail = JobBuilder.newJob(OneHourQuartzJob.class).withIdentity(jobName, groupName).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName)
            .withSchedule(cronSchedule(CronConst.ONE_HOUR)).forJob(jobDetail.getKey()).build();

        addJob(jobDetail, trigger);
        logger.info("初始化整点任务...");
    }

    public void addJob(JobDetail jobDetail, Trigger trigger) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.warn("QuartzService 定时任务执行出错");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
