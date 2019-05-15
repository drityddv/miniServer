package utils;

import java.util.UUID;

/**
 * 临时的id,key,生成工具
 *
 * @author : ddv
 * @since : 2019/5/7 上午10:10
 */

public class IdUtil {

    public static long getLongId() {
        return UUID.randomUUID().hashCode();
    }

}
