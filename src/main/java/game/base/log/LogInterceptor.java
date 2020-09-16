package game.base.log;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author : ddv
 * @since : 2020/3/5 4:32 PM
 */

public class LogInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 只拦截log方法。
        if (!method.getName().equals("log")) {
            return methodProxy.invokeSuper(o, objects);
        }
        objects[0] = MyLogger.FQCN;
        return methodProxy.invokeSuper(o, objects);
    }
}
