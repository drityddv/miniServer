package quartz.job.common.scene;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import game.base.executor.command.constant.ExecutorConstant;
import game.base.executor.util.ExecutorUtils;
import game.world.base.command.scene.ReliveCommand;

/**
 * @author : ddv
 * @since : 2019/7/25 5:03 PM
 */

public class SceneReliveJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ReliveCommand command = (ReliveCommand)context.getMergedJobDataMap().get(ExecutorConstant.COMMAND);
        ExecutorUtils.submit(command);
    }
}
