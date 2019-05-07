package game.gm.service;

import game.gm.packet.CM_GmCommand;
import net.model.USession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spring.SpringContext;
import utils.JodaUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : ddv
 * @since : 2019/5/7 下午2:49
 */
@Component
public class GmService implements IGmService {

	private static final Logger logger = LoggerFactory.getLogger(GmService.class);

	@Override
	public void invoke(USession session, CM_GmCommand request) {
		String[] split = request.getMethodAndParams().split(" ");
		for (String word : split) {
			System.out.print(word + " ");
		}

		String methodName = split[0];

		// 先循环遍历,,后期用map存储,增加热更等功能
		Method[] declaredMethods = SpringContext.getGmCommand().getClass().getDeclaredMethods();

		for (Method method : declaredMethods) {
			if (method.getName().equals(methodName)) {
				try {
					doInvoke(session, method, split);
				} catch (Exception e) {
					logger.error("gm命令调用出错,指令[{}],参数个数[{}]", methodName, split.length);
				}
			}
		}
	}

	// params 记得从下标1开始走 , 0用来占位方法名
	private void doInvoke(USession session, Method method, String[] params) throws InvocationTargetException, IllegalAccessException {
		GM_Command gmCommand = SpringContext.getGmCommand();
		Class<?>[] parameterTypes = method.getParameterTypes();

		// gm命令都用USession占了一个参数位 目前出去session 最大三个参数
		switch (parameterTypes.length - 1) {
			case 0: {
				method.invoke(gmCommand, session);
				break;
			}
			case 1: {
				method.invoke(gmCommand, session, JodaUtil.convertFromString(parameterTypes[1], params[1]));
				break;
			}
			case 2: {
				method.invoke(gmCommand, session, JodaUtil.convertFromString(parameterTypes[1], params[1]),
						JodaUtil.convertFromString(parameterTypes[2], params[2]));
				break;
			}
			case 3: {
				method.invoke(gmCommand, session, JodaUtil.convertFromString(parameterTypes[1], params[1]),
						JodaUtil.convertFromString(parameterTypes[2], params[2]),
						JodaUtil.convertFromString(parameterTypes[3], params[3]));
				break;
			}
			default: {
				logger.error("gm命令调用出错,指令[{}],参数个数[{}],超过最大长度", method.getName(), params.length - 1);
				break;
			}
		}


	}
}
