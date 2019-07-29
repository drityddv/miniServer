package scheduler.job.common.scene;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.command.constant.ExecutorConstant;
import game.base.executor.command.impl.scene.impl.rate.SceneHeartBeatCommand;
import game.base.executor.util.ExecutorUtils;

/**
 * 场景周期心跳任务
 *
 * @author : ddv
 * @since : 2019/7/15 9:44 AM
 */

public class SceneHeartBeatRateJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SceneHeartBeatRateJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        SceneHeartBeatCommand command =
            (SceneHeartBeatCommand)context.getMergedJobDataMap().get(ExecutorConstant.COMMAND);
        logger.info("地图[{}] 心跳任务", command.getMapId());
        ExecutorUtils.submit(command);
    }
}
