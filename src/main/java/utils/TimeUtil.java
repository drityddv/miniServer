package utils;

import io.protostuff.Morph;
import net.utils.ProtoStuffUtil;

import java.time.Instant;

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

    static class A{
    	int a;
    	@Morph
    	String b;
	}

    public static void main(String[] args){
		A a = new A();
		a.a = 10;
		a.b="b";
		byte[] serialize = ProtoStuffUtil.serialize(a);

		a = ProtoStuffUtil.deserialize(serialize,A.class);


	}
}
