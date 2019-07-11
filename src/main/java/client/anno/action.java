package client.anno;

import java.lang.annotation.*;

/**
 * @author : ddv
 * @since : 2019/7/10 下午10:55
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface action {}
