import middleware.dispatch.Dispatcher;
import net.server.Server;
import spring.SpringContext;
import spring.SpringController;
import utils.IdUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : ddv
 * @since : 2019/4/29 下午3:16
 */

public class Start {
	public static void main(String[] args){
		// 初始化spring管理中心
		SpringController.initHandlerDestinationMap();

		//net服务器启动
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

		singleThreadExecutor.submit(() -> {
			Server server = new Server();
			server.init();
			server.run();
		});

		System.out.println("main thread shutdown");
	}
}
