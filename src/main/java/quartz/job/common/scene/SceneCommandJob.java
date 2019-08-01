package quartz.job.common.scene;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import game.base.executor.command.constant.ExecutorConstant;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.executor.util.ExecutorUtils;

/**
 * @author : ddv
 * @since : 2019/7/31 4:31 PM
 */

public class SceneCommandJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AbstractSceneCommand command =
            (AbstractSceneCommand)context.getMergedJobDataMap().get(ExecutorConstant.COMMAND);
        ExecutorUtils.submit(command);
    }
}
