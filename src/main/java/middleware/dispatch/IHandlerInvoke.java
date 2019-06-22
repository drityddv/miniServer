package middleware.dispatch;

/**
 * @author : ddv
 * @since : 2019/4/28 下午11:30
 */

public interface IHandlerInvoke {

    /**
     * 方法invoke
     *
     * @param args
     * @return
     */
    Object invoke(Object... args);
}
