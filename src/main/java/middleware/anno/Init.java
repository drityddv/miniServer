package middleware.anno;

import java.lang.annotation.*;

/**
 * 静态资源的二次加载
 *
 * @author : ddv
 * @since : 2019/6/23 上午3:38
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Init {}
