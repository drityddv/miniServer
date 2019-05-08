package middleware.dispatch;

import net.model.USession;

/**
 * @author : ddv
 * @since : 2019/4/28 下午11:30
 */

public interface IHandlerInvoke {

    /**
     * 方法invoke
     *
     * @param session
     * @param packet
     * @return
     */
    Object invoke(USession session, Object packet);
}
