package middleware.anno;

import java.lang.annotation.*;

import org.springframework.stereotype.Component;

/**
 * 资源对象注解
 *
 * @author : ddv
 * @since : 2019/5/9 下午9:45
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Resource {
    String getName() default "";
}
