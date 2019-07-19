package scheduler.job.model;

import org.quartz.listeners.TriggerListenerSupport;
import org.slf4j.Logger;

import scheduler.constant.JobGroupEnum;

/**
 * @author : ddv
 * @since : 2019/7/18 11:01 AM
 */

public class BuffTriggerListener extends TriggerListenerSupport {
    private final Logger logger = super.getLog();

    @Override
    public String getName() {
        return JobGroupEnum.BUFF.name();
    }
}
