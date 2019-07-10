package utils.snow;

/**
 * 临时的id,key,生成工具
 *
 * @author : ddv
 * @since : 2019/5/7 上午10:10
 */

public class IdUtil {

    private static SnowFlake snowFlake = new SnowFlake(1, 1);

    public static long getLongId() {
        return snowFlake.nextId();
    }

}
