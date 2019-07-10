package utils;

import java.util.Map;

import org.quartz.*;

import game.role.player.model.Player;
import scheduler.job.model.JobEntity;
import utils.snow.IdUtil;

/**
 * @author : ddv
 * @since : 2019/7/9 上午12:12
 */

public class QuartzUtil {

    public static JobEntity build(Class<? extends Job> jobClass, String jobName, String groupName,
        Map<String, Object> params, long delay, int rate) {
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(IdUtil.getLongId() + "", groupName).build();

        jobDetail.getJobDataMap().put("player", Player.valueOf("ddv"));

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(IdUtil.getLongId() + "", groupName).startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(delay).repeatForever())
            .build();
        return JobEntity.valueOf(jobDetail, trigger);
    }

}
