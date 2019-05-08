package utils;

import org.joda.convert.StringConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/5/7 下午4:23
 */

public class JodaUtil {

    private static final Logger logger = LoggerFactory.getLogger(JodaUtil.class);

    // 使用者调用需要检查结果是否为null
    public static <T> T convertFromString(Class<T> clazz, String word) {
        T result = null;
        try {
            result = StringConvert.INSTANCE.convertFromString(clazz, word);
        } catch (Exception e) {
            logger.error("joda convert error,form string [{}],to class [{}]", word, clazz.getName());
        }
        return result;
    }
}
