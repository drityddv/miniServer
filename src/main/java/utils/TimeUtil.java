package utils;

import java.time.Instant;

import io.protostuff.Morph;
import net.utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/4/29 下午4:14
 */

public class TimeUtil {

    /**
     * 返回系统当前时间戳
     *
     * @return
     */
    public static long now() {
        return Instant.now().toEpochMilli();
    }

    public static void main(String[] args) {
        A a = new A();
        a.a = 10;
        a.b = "b";
        byte[] serialize = ProtoStuffUtil.serialize(a);

        a = ProtoStuffUtil.deserialize(serialize, A.class);

    }

    static class A {
        int a;
        @Morph
        String b;
    }
}
