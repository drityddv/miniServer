import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MathUtil;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void run() {
		System.out.println(MathUtil.getGcd(1,333));
		System.out.println(MathUtil.getGcd(3,333));
    }



}
