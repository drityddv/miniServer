package utils;

import java.time.Instant;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author : ddv
 * @since : 2019/4/29 下午4:14
 */

public class TimeUtil {

    /**
     * 返回系统当前时间戳
     *
     * @return
     */
    public static long now() {
        return Instant.now().toEpochMilli();
    }

    public static void main(String[] args) {
        // 定义一个JobDetail
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
            // 定义name和group
            .withIdentity("job1", "group1")
            // job需要传递的内容
            .usingJobData("name", "ddv").build();
        // 定义一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
            // 加入 scheduler之后立刻执行
            .startNow()
            // 定时 ，每个1秒钟执行一次
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
                // 重复执行
                .repeatForever())
            .build();
        try {
            // 创建scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start(); // 运行一段时间后关闭
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

}
