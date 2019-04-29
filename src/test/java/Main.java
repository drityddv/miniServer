import middleware.dispatch.Dispatcher;
import middleware.dispatch.HandlerInvoke;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import spring.SpringContext;
import spring.SpringInitCenter;

import java.time.Instant;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/4/26 下午9:09
 */

public class Main {

	public static void main(String[] args) {
		SpringInitCenter springInitCenter = SpringInitCenter.getInstance();
		springInitCenter.initHandlerDestinationMap();
		ApplicationContext context = SpringInitCenter.getContext();
		Dispatcher dispatcher = context.getBean(Dispatcher.class);
		Map<Class<?>, HandlerInvoke> map = dispatcher.getHandlerDestinationMap();

		map.forEach((aClass, handlerInvoke) -> handlerInvoke.invoke(null, null));

		System.out.println("1");
	}

	@Test
	public void run(){
		SpringInitCenter.initHandlerDestinationMap();
		Dispatcher dispatcher = SpringContext.getDispatcher();
		System.out.println(1);
	}
}
