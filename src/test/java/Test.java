import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.user.player.model.Player;
import utils.StringUtil;

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

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void run() {
        Player player = Player.valueOf("ddv");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                int level = player.getLevel();
                System.out.println(StringUtil.wipePlaceholder("当前等级[{}] hashcode[{}] 设置后的等级[{}]", level,
                    player.hashCode(), player.setLevel(level + 1)));
            });
        }

        System.out.println(player.getLevel());
    }

}
