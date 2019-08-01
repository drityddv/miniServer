package quartz.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.executor.command.impl.scene.impl.rate.SceneHeartBeatCommand;
import game.world.base.service.WorldManager;
import quartz.constant.CronConst;
import quartz.constant.JobGroupEnum;
import quartz.job.common.server.OneHourQuartzJob;
import quartz.job.model.JobEntry;
import utils.snow.IdUtil;

/**
 * 定时调度服务
 *
 * @author : ddv
 * @since : 2019/7/8 下午11:05
 */
@Component
public class QuartzService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Scheduler scheduler;

    // FIXME quartz监听是真的🐔 居然没有后置回调 现在周期性buff清除逻辑先写在buff逻辑里吧
    public void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            initOneHourJob();
            initSceneHearJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void initSceneHearJob() {
        WorldManager.getInstance().getMapResources().forEach(mapResource -> {
            JobEntry.newSceneRateJob(1000 * 20, 0, SceneHeartBeatCommand.valueOf(mapResource.getMapId())).schedule();
        });
    }

    // 整点任务
    private void initOneHourJob() {
        String groupName = JobGroupEnum.PUBLIC_COMMON.name();
        String jobName = IdUtil.getLongId() + "";
        String triggerName = IdUtil.getLongId() + "";

        JobDetail jobDetail = JobBuilder.newJob(OneHourQuartzJob.class).withIdentity(jobName, groupName).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName)
            .withSchedule(cronSchedule(CronConst.ONE_HOUR)).startNow().forJob(jobDetail.getKey()).build();

        addJob(jobDetail, trigger);
        logger.info("初始化整点任务...");
    }

    public void removeJob(JobDetail jobDetail) {
        logger.info("取消作业[{}]", jobDetail.getKey().getName());
        try {
            scheduler.deleteJob(jobDetail.getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addJob(JobDetail jobDetail, Trigger trigger) {
        logger.info("新的调度作业[{}]", jobDetail.getKey().getName());
        if (trigger.getEndTime() != null) {
            logger.info("触发器结束[{}]", trigger.getEndTime().getTime());
        }
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error("提交调度任务异常");
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

    public void scheduleJob(JobEntry jobEntry) {
        addJob(jobEntry.getJobDetail(), jobEntry.getTrigger());
    }
}
