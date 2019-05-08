package utils;

import net.model.USession;

/**
 * 无分类的工具包
 *
 * @author : ddv
 * @since : 2019/5/8 上午11:18
 */

public class SimpleUtil {

	public static String getAccountIdFromSession(USession session){
		return (String) session.getAttributes().get("accountId");
	}

}
