package game.common.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import scheduler.QuartzService;
import scheduler.constant.JobGroupEnum;
import scheduler.job.common.CronConst;
import scheduler.job.common.OneHourQuartzJob;
import utils.snow.IdUtil;

/**
 * 公共服务
 *
 * @author : ddv
 * @since : 2019/7/8 下午9:02
 */
@Component
public class CommonService implements ICommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private CommonManager commonManager;

    @Autowired
    private QuartzService quartzService;

    @Override
    public AbstractItem createItem(long configId, int num) {
        ItemResource itemResource = commonManager.getItemResource(configId);
        if (itemResource == null) {
            logger.warn("配置表id:[{}]的资源文件不存在", configId);
            RequestException.throwException(I18N.RESOURCE_NOT_EXIST);
        }
        AbstractItem abstractItem = itemResource.getItemType().create();
        abstractItem.setObjectId(IdUtil.getLongId());
        abstractItem.init(itemResource);
        abstractItem.setNum(num);
        return abstractItem;
    }

    @Override
    public List<AbstractItem> createItems(long configId, int num) {
        List<AbstractItem> items = new ArrayList<>();
        while (num > 0) {
            items.add(createItem(configId, 1));
            num--;
        }
        return items;
    }

    @Override
    public void initPublicTask() {
        initOneHourJob();
    }

    // 整点任务
    private void initOneHourJob() {
        String groupName = JobGroupEnum.PUBLIC_COMMON.name();
        String jobName = IdUtil.getLongId() + "";
        String triggerName = IdUtil.getLongId() + "";

        JobDetail jobDetail = JobBuilder.newJob(OneHourQuartzJob.class).withIdentity(jobName, groupName).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName)
            .withSchedule(cronSchedule(CronConst.ONE_HOUR)).forJob(jobDetail.getKey()).build();

        quartzService.addJob(jobDetail, trigger);
        logger.info("初始化整点任务...");
    }

    @Override
    public void oneHourJob() {
        logger.info("服务器抛出整点事件...");
    }
}
