package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 *
 * @author : ddv
 * @since : 2019/4/25 上午9:53
 */

public class CollectionUtil {

    /**
     * 容器是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotBlank(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    public static <T> List<T> emptyArrayList() {
        return new ArrayList<>();
    }
}
