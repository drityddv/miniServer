package ebus.anno;

import java.lang.annotation.*;

/**
 * 事件消费者注解
 *
 * @author : ddv
 * @since : 2019/6/22 下午7:24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EventReceiver {}
