package game.base.executor.service;

import middleware.dispatch.HandlerInvoke;
import net.model.USession;

/**
 * @author : ddv
 * @since : 2019/6/18 下午11:32
 */

public interface IMiniExecutorService {

    /**
     * 线程池接口接受任务
     *
     * @param handlerInvoke
     * @param session
     * @param packet
     * @param id
     */
    void handle(HandlerInvoke handlerInvoke, USession session, Object packet, int id);
}
