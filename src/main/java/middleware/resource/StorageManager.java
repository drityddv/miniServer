package middleware.resource;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import middleware.anno.Init;
import middleware.anno.MiniResource;
import middleware.anno.Static;
import middleware.resource.model.ResourceDefinition;
import spring.SpringContext;
import spring.SpringController;
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
public class StorageManager {
    private static final Logger logger = LoggerFactory.getLogger(StorageManager.class);

    @Autowired
    private ApplicationContext context;
    // 静态类加载容器
    private Map<Class<?>, ResourceDefinition> definitionMap = new HashMap<>();

    // 静态资源存放容器
    private Map<Class<?>, Storage<?, ?>> storageMap = new HashMap<>();

    // csv读取的缓存流容器
    private Map<String, InputStream> caches = new HashMap<>();

    // 静态资源目的地加载
    private static void initResourceDefinition() {
        StorageManager storageManager = SpringContext.getStorageManager();

        // 加载普通静态资源
        List<Class<?>> simpleClasses = SpringController.getBeanClasses().stream()
            .filter(aClass -> aClass.getAnnotation(MiniResource.class) != null).collect(Collectors.toList());

        simpleClasses.forEach(aClass -> {
            ResourceDefinition definition = new ResourceDefinition(aClass);
            storageManager.registerDefinition(aClass, definition);
        });

        logger.info("初始化普通静态资源文件目的地完毕,加载了[{}]条注解数据", storageManager.getDefinitionMap().size());
    }

    // 注册普通静态资源类目的地
    private void registerDefinition(Class<?> beanClass, ResourceDefinition definition) {
        if (!definitionMap.containsKey(beanClass)) {
            definitionMap.put(beanClass, definition);
            return;
        }
        logger.error("重复的加载行为,class[{}]", beanClass);
    }

    private void registerCsvCache(String location, InputStream inputStream) {
        if (!caches.containsKey(location)) {
            caches.put(location, inputStream);
            return;
        }
        logger.error("重复加载,忽略此次注册,location[{}]", location);
    }

    public void init() {
        initResourceDefinition();
        initCsvCache();
        initSimpleResource();
        initServiceMap();
    }

    // 初始化静态资源类
    private void initSimpleResource() {
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
    private void storageResourceSecondInit() {
        storageMap.forEach((aClass, storage) -> {
            storage.getStorageMap().forEach((o, o2) -> {
                Method method = ClassUtil.getMethodByAnnotation(o2, Init.class);
                if (method != null) {
                    method.setAccessible(true);
                    ReflectionUtils.invokeMethod(method, o2);
                }
            });
        });
    }

    // 普通静态资源csv加载
    private void initCsvCache() {
        StorageManager storageManager = SpringContext.getStorageManager();
        Map<Class<?>, ResourceDefinition> definitionMap = storageManager.getDefinitionMap();
        registerCsvCaches(storageManager, definitionMap);
    }

    private void registerCsvCaches(StorageManager storageManager,
        Map<Class<?>, ResourceDefinition> resourceDefinitionMap) {
        resourceDefinitionMap.forEach((aClass, resourceDefinition) -> {
            String location = resourceDefinition.getLocation();
            InputStream inputStream = SimpleUtil.getInputStreamFromFile(location);
            storageManager.registerCsvCache(location, inputStream);
            logger.info("初始化csv文件流结束,csv文件个数[{}],[{}]", resourceDefinitionMap.size(),
                resourceDefinition.getClz().getSimpleName());
        });
    }

    // service中的静态资源赋值引用
    private void initServiceMap() {
        SpringController.getBeanClasses().forEach(aClass -> {
            Object contextBean = context.getBean(aClass);
            List<Field> fields = ClassUtil.getFieldsByAnnotation(contextBean, Static.class);
            fields.forEach(field -> {
                if (field != null) {
                    ParameterizedType genericType = (ParameterizedType)field.getGenericType();
                    Type[] actualTypeArguments = genericType.getActualTypeArguments();
                    String valueType = actualTypeArguments[1].getTypeName();
                    Map<Class<?>, Storage<?, ?>> storageMap = SpringContext.getStorageManager().getStorageMap();

                    try {
                        Storage<?, ?> storage = storageMap.get(Class.forName(valueType));
                        field.set(contextBean, storage.getStorageMap());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        });

    }

    // get
    private Map<Class<?>, ResourceDefinition> getDefinitionMap() {
        return definitionMap;
    }

    private Map<Class<?>, Storage<?, ?>> getStorageMap() {
        return storageMap;
    }

}
