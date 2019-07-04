package game.base.executor.service;

import middleware.dispatch.HandlerInvoke;

/**
 * @author : ddv
 * @since : 2019/6/18 下午11:32
 */

public interface IMiniExecutorService {

    /**
     * 调度账号线程池
     *
     * @param handlerInvoke
     * @param param
     *            [登陆,注册这里给的都是session 其他都是Player]
     * @param modIndex
     * @param packet
     */
    void handle(HandlerInvoke handlerInvoke, Object param, int modIndex, Object packet);
}
