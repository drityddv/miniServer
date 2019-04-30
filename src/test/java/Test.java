import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

	@org.junit.Test
	public void run(){
		Map<String,Integer> map = new ConcurrentHashMap<>();

		map.put("1",1);
		map.put("2",2);

		int id = map.get("3");
		System.out.println(id);
	}
}
