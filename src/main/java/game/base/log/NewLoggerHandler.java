package game.base.log;

import java.lang.reflect.Method;

/**
 * @author : ddv
 * @since : 2020/3/5 4:29 PM
 */

public class NewLoggerHandler implements java.lang.reflect.InvocationHandler {
    private final org.apache.log4j.Logger proxyLogger;

    public NewLoggerHandler(org.apache.log4j.Logger proxyLogger) {
        this.proxyLogger = proxyLogger;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return proxyLogger;
    }
}
