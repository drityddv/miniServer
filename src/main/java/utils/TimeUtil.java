package utils;

import java.time.Instant;

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
}