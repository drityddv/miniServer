package scheduler.job.common.scene;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import game.base.executor.util.ExecutorUtils;
import game.world.fight.command.BuffActiveCommand;

/**
 * buff效果工作
 *
 * @author : ddv
 * @since : 2019/7/17 4:04 PM
 */

public class BuffEffectJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        BuffActiveCommand command = (BuffActiveCommand)context.getMergedJobDataMap().get("command");
        ExecutorUtils.submit(command);
    }
}
