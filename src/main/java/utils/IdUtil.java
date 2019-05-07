package utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 临时的id,key,生成工具
 * @author : ddv
 * @since : 2019/5/7 上午10:10
 */

public class IdUtil {

	private static AtomicLong atomicLong = new AtomicLong();

	public static long getLongId() {
		return atomicLong.getAndIncrement();
	}

}
