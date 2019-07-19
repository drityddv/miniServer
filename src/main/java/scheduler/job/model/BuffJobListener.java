package scheduler.job.model;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;

import game.base.effect.model.BaseBuffEffect;
import game.world.fight.command.BuffActiveCommand;
import scheduler.constant.JobGroupEnum;

/**
 * @author : ddv
 * @since : 2019/7/18 11:11 AM
 */

public class BuffJobListener extends JobListenerSupport {
    private final Logger logger = super.getLog();

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        BuffActiveCommand command = (BuffActiveCommand)context.getMergedJobDataMap().get("command");
        if (command.getRemainCount() >= 0) {
            return;
        }

        BaseBuffEffect buff = command.getBuff();
        logger.info("buff[{}] 执行周期结束 即将清除地图存储信息与buff效果", buff.getJobId());
        buff.getMapBuffGroup().remove(buff.getJobId());
        buff.removeBuff();
    }

    @Override
    public String getName() {
        return JobGroupEnum.BUFF.name();
    }
}
