import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import utils.ResourceUtil;

import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);
	@org.junit.Test
    public void run(){
		Map<String, Object> ymlRoot = ResourceUtil.getYmlRoot("server-dev");
	}
}
