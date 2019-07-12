package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;

/**
 * @author : ddv
 * @since : 2019/7/12 3:54 PM
 */

public class DozerUtil {
    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */

    private static DozerBeanMapper dozer = (DozerBeanMapper)DozerBeanMapperBuilder.buildDefault();;

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    public static void main(String[] args) {
        log();
    }

    public static void log() {

    }

}
