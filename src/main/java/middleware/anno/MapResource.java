package middleware.anno;

import java.lang.annotation.*;

import org.springframework.stereotype.Component;

import game.base.map.IMap;

/**
 * 地图资源对象注解
 *
 * @author : ddv
 * @since : 2019/5/9 下午9:45
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface MapResource {
    String name() default "MapResource";

    Class clazz() default IMap.class;
}
