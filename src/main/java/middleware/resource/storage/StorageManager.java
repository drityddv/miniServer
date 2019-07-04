package middleware.resource.storage;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import middleware.anno.Init;
import middleware.resource.middle.ResourceDefinition;
import utils.ClassUtil;
import utils.ResourceUtil;
import utils.SimpleUtil;

/**
 * 静态资源管理
 *
 * @author : ddv
 * @since : 2019/5/9 下午2:39
 */
@Component
public class StorageManager implements ApplicationListener<ApplicationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StorageManager.class);

    // 普通静态类容器
    private Map<Class<?>, ResourceDefinition> definitionMap = new ConcurrentHashMap<>();

    // 静态资源存储容器
    private Map<Class<?>, Storage<?, ?>> storageMap = new ConcurrentHashMap<>();

    // csv读取的缓存流容器
    private Map<String, InputStream> caches = new ConcurrentHashMap<>();

    // 注册普通静态资源类目的地
    public void registerDefinition(Class<?> beanClass, ResourceDefinition definition) {
        if (!definitionMap.containsKey(beanClass)) {
            definitionMap.put(beanClass, definition);
            return;
        }
        logger.error("重复的加载行为,class[{}]", beanClass);
    }

    public void registerCsvCache(String location, InputStream inputStream) {
        if (!caches.containsKey(location)) {
            caches.put(location, inputStream);
            return;
        }
        logger.error("重复加载,忽略此次注册,location[{}]", location);
    }

    // 初始化静态资源类
    public void initSimpleResource() {
        definitionMap.forEach((aClass, resourceDefinition) -> {
            String resourceLocation = resourceDefinition.getLocation();
            InputStream inputStream = caches.get(resourceLocation);
            List objectList = ResourceUtil.loadObjectFromCsv(aClass, SimpleUtil.getParserFromStream(inputStream));
            storageMap.put(aClass, Storage.valueOf(objectList));

            logger.info("从缓存流生成java对象完毕,生成数量[{}],对象[{}]", objectList.size(), aClass.getSimpleName());
        });
        storageResourceSecondInit();
    }

    // 注入static字段引用
    public void storageResourceSecondInit() {
        storageMap.forEach((aClass, storage) -> {
            storage.getStorageListMap().forEach((o, objects) -> {
                objects.forEach(o1 -> {
                    Method method = ClassUtil.getMethodByAnnotation(o1, Init.class);
                    if (method != null) {
                        method.setAccessible(true);
                        ReflectionUtils.invokeMethod(method, o1);
                    }
                });
            });

            storage.getStorageMap().forEach((o, o2) -> {
                Method method = ClassUtil.getMethodByAnnotation(o2, Init.class);
                if (method != null) {
                    method.setAccessible(true);
                    ReflectionUtils.invokeMethod(method, o2);
                }
            });

        });

    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }

    // get
    public Map<Class<?>, ResourceDefinition> getDefinitionMap() {
        return definitionMap;
    }

    public Map<Class<?>, Storage<?, ?>> getStorageMap() {
        return storageMap;
    }

    public Map<String, InputStream> getCaches() {
        return caches;
    }

}
