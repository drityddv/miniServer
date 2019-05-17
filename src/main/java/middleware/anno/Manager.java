package middleware.anno;

import java.lang.annotation.*;

/**
 * @author : ddv
 * @since : 2019/5/13 下午2:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Manager {
    // init();

}
