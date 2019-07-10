package client.anno;

import java.lang.annotation.*;

/**
 * 客户端解析响应包的逻辑
 *
 * @author : ddv
 * @since : 2019/7/10 下午4:36
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Action {}
