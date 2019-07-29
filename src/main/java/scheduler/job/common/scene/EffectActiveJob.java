package scheduler.job.common.scene;

import game.base.executor.command.constant.ExecutorConstant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.util.ExecutorUtils;
import game.world.fight.command.buff.BuffActiveCommand;

/**
 * 效果生效任务 延迟和周期性都用这个做
 *
 * @author : ddv
 * @since : 2019/7/17 4:04 PM
 */

public class EffectActiveJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(EffectActiveJob.class);
    private BuffActiveCommand command;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        command = (BuffActiveCommand)context.getMergedJobDataMap().get(ExecutorConstant.COMMAND);
        ExecutorUtils.submit(command);
    }

}
