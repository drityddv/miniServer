package game.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 公共服务
 *
 * @author : ddv
 * @since : 2019/7/8 下午9:02
 */
@Component
public class CommonService implements ICommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Override
    public void oneHourJob() {
        logger.info("服务器抛出整点事件...");
    }

}
