package resource.anno;

import java.lang.annotation.*;

import org.springframework.stereotype.Component;

/**
 * @author : ddv
 * @since : 2019/6/11 下午2:15
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface MiniResource {}
