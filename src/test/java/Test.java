import utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */
class B {
    transient Map map = new HashMap();
}

class A {
    int age;

    @Override
    public int hashCode() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof A)) {
            return false;
        }

        A o = (A)obj;
        return this.age == o.age;
    }
}

public class Test {

    @org.junit.Test
    public void run() {
		String accountId = StringUtil.wipePlaceholder("打印地图[{}],玩家正在切入[{}],[{}]", 1, "accountId", 2L);
		System.out.println(accountId);
	}

}
