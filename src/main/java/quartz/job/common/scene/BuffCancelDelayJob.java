package quartz.job.common.scene;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import game.base.executor.command.constant.ExecutorConstant;
import game.base.executor.util.ExecutorUtils;
import game.world.fight.command.buff.BuffCancelCommand;

/**
 * buff关闭job
 *
 * @author : ddv
 * @since : 2019/7/18 12:22 PM
 */

public class BuffCancelDelayJob implements Job {
    private BuffCancelCommand command;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        command = (BuffCancelCommand)context.getMergedJobDataMap().get(ExecutorConstant.COMMAND);
        ExecutorUtils.submit(command);
    }
}
