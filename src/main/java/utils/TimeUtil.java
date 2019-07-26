package utils;

import java.time.Instant;
import java.time.LocalTime;

/**
 * @author : ddv
 * @since : 2019/4/29 下午4:14
 */

public class TimeUtil {

    /**
     * 返回系统当前时间戳
     *
     * @return
     */
    public static long now() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取时间戳比现在溢出的毫秒数
     *
     * @param timeStamp
     * @return
     */
    public static long getMillisByStamp(long timeStamp) {
        long now = now();
        if (timeStamp < now) {
            return 0;
        }
        return (timeStamp - now);
    }

    public static void main(String[] args) {
        long now = TimeUtil.now();

    }

    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

}
