package middleware.dispatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用层请求派发器
 *
 * @author : ddv
 * @since : 2019/4/28 下午9:05
 */

@Component
public class Dispatcher {

	private static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	/**
	 * 方法invoke对应表
	 */
	private Map<Class<?>, HandlerInvoke> handlerDestinationMap = new HashMap<>();

	public Map<Class<?>, HandlerInvoke> getHandlerDestinationMap() {
		return handlerDestinationMap;
	}

	public void addHandlerDestination(Class<?> clazz, HandlerInvoke handlerInvoke) {
		if (handlerDestinationMap.containsKey(clazz)) {
			logger.error("重复添加的clazz{}", clazz.toString());
			throw new RuntimeException("初始化应用层派发器出错!");
		}

		handlerDestinationMap.put(clazz, handlerInvoke);
	}

	public void invoke(Class<?> packet) {
		HandlerInvoke handlerInvoke = handlerDestinationMap.get(packet);

		if (handlerInvoke == null) {
			logger.error("协议[{}]未注册handlerMap,忽视此条消息!", packet.getClass());
		}

		handlerInvoke.invoke(null, packet);
	}
}
