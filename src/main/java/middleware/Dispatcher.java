package middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用层请求派发器
 *
 * @author : ddv
 * @since : 2019/4/28 下午9:05
 */

public class Dispatcher {

	private static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	private static Map<Class<?>, Method> handlerDestinationMap = new HashMap<>();
}
