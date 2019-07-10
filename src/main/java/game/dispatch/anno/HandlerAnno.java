package game.dispatch.anno;

import java.lang.annotation.*;

/**
 * @author : ddv
 * @since : 2019/4/28 下午8:55
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HandlerAnno {}
