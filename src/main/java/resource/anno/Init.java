package resource.anno;

import java.lang.annotation.*;

/**
 * 静态资源解析
 *
 * @author : ddv
 * @since : 2019/6/23 上午3:38
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Init {}
