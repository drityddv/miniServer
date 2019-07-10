package resource.anno;

import java.lang.annotation.*;

/**
 *
 * @author : ddv
 * @since : 2019/6/23 下午8:26
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Static {}
