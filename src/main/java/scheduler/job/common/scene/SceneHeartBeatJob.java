package scheduler.job.common.scene;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.command.impl.scene.impl.rate.SceneHeartBeatCommand;
import game.base.executor.util.ExecutorUtils;

/**
 * @author : ddv
 * @since : 2019/7/15 9:44 AM
 */

public class SceneHeartBeatJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SceneHeartBeatJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SceneHeartBeatCommand command = (SceneHeartBeatCommand)context.getMergedJobDataMap().get("command");
        logger.info("地图[{}]心跳调度", command.getMapId());
        ExecutorUtils.submit(command);
    }
}
