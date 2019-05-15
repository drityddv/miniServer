package utils;

import game.user.player.model.Player;
import net.model.USession;
import spring.SpringContext;

/**
 * 无分类的工具包
 *
 * @author : ddv
 * @since : 2019/5/8 上午11:18
 */

public class SimpleUtil {

    public static String getAccountIdFromSession(USession session) {
        return (String)session.getAttributes().get("accountId");
    }

    public static Player getPlayerFromSession(USession session) {
        String accountId = getAccountIdFromSession(session);
        return SpringContext.getPlayerService().getPlayerByAccountId(accountId);
    }

}
