package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/12/17 2:51 PM
 */

public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void log(String logs, Object... params) {
        logger.info(logs, params);
    }
}
