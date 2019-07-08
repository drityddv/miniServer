package utils;

import net.model.USession;

/**
 * @author : ddv
 * @since : 2019/7/8 下午3:00
 */

public class SessionUtil {

    public static String getAccountIdFromSession(USession session) {
        return (String)session.getAttributes().get("accountId");
    }
}
