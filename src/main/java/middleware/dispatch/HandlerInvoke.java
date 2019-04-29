package middleware.dispatch;

import net.model.USession;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author : ddv
 * @since : 2019/4/29 上午10:12
 */

public class HandlerInvoke implements IHandlerInvoke {

	/**
	 * 目标反射方法的所属对象
	 */
	private final Object bean;
	/**
	 * 反射的方法
	 */
	private final Method method;
	/**
	 * 对应packet
	 */
	private final Class<?> clazz;

	public HandlerInvoke(Object bean, Method method, Class<?> clazz) {
		this.bean = bean;
		this.method = method;
		this.clazz = clazz;
	}

	public Object getBean() {
		return bean;
	}

	public Method getMethod() {
		return method;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	@Override
	public Object invoke(USession session, Object packet) {
		return ReflectionUtils.invokeMethod(method, bean, session, packet);
	}

	public static HandlerInvoke createHandlerInvoke(Object bean, Method method, Class<?> clazz) {
		return new HandlerInvoke(bean, method, clazz);
	}
}
