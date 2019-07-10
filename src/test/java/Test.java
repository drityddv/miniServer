import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void run() throws SchedulerException {
		System.out.println(Integer.MAX_VALUE);
    }

}
