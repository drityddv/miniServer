import middleware.dispatch.Dispatcher;
import spring.SpringContext;
import spring.SpringInitCenter;

/**
 * @author : ddv
 * @since : 2019/4/29 下午3:16
 */

public class Main {
	public static void main(String[] args){
		SpringInitCenter.initHandlerDestinationMap();
		Dispatcher dispatcher = SpringContext.getDispatcher();
	}
}
