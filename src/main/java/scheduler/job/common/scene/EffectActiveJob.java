package scheduler.job.common.scene;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.TriggerListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.util.ExecutorUtils;
import game.world.fight.command.BuffActiveCommand;

/**
 * 效果生效任务 延迟和周期性都用这个做
 *
 * @author : ddv
 * @since : 2019/7/17 4:04 PM
 */

public class EffectActiveJob extends TriggerListenerSupport implements Job {
    private static final Logger logger = LoggerFactory.getLogger(EffectActiveJob.class);
    BuffActiveCommand command;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        command = (BuffActiveCommand)context.getMergedJobDataMap().get("command");
        logger.info("jobId[{}] 剩余次数[{}] ", command.getBuff().getJobId(), command.getRemainCount());
        ExecutorUtils.submit(command);
    }

    @Override
    public String getName() {
        return command.getBuff().getJobId() + "";
    }

}
