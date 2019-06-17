package spring;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import game.base.map.IMap;
import game.scene.map.service.SceneMapManager;
import middleware.anno.HandlerAnno;
import middleware.anno.MiniResource;
import middleware.dispatch.Dispatcher;
import middleware.dispatch.HandlerInvoke;
import middleware.resource.middle.ResourceDefinition;
import middleware.resource.storage.StorageManager;
import utils.ClassUtil;
import utils.SimpleUtil;

/**
 * @author : ddv
 * @since : 2019/4/28 下午3:28
 */

public class SpringController {

    private static final Logger logger = LoggerFactory.getLogger(SpringController.class);

    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");

    private static SpringController instance = new SpringController();

    private static List<String> beanNames = Arrays.asList(CONTEXT.getBeanDefinitionNames());

    private static List<Class<?>> beanClasses = null;

    static {
        initBeanClasses();
        initHandlerDestinationMap();
        initResourceDefinition();
        initMapCsvCaches();
        // initCsvCache();
        // initStaticResource();
        initMapResourceManager();
    }

    private SpringController() {}

    private static void initBeanClasses() {
        beanClasses = new ArrayList<>();
        beanNames.forEach(beanName -> {
            beanClasses.add(CONTEXT.getBean(beanName).getClass());
        });
    }

    public static SpringController getInstance() {
        return instance;
    }

    public static ApplicationContext getContext() {
        return CONTEXT;
    }

    public static void init() {
        logger.info("spring模块加载结束...");
    }

    /**
     * 初始化派发器的handlerMap
     */
    private static void initHandlerDestinationMap() {
        Dispatcher dispatcher = CONTEXT.getBean(Dispatcher.class);
        int totalHandlerMapSize = 0;

        for (String name : beanNames) {
            Object bean = CONTEXT.getBean(name);
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();

            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                if (method.isAnnotationPresent(HandlerAnno.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    if (parameterTypes == null || parameterTypes.length != 2) {
                        logger.error("HandlerAnno注解处理出错,{}方法有误!", method);
                        throw new RuntimeException("facade方法参数定义有误!");
                    }

                    Class<?> aClass = parameterTypes[1];

                    dispatcher.addHandlerDestination(aClass, HandlerInvoke.createHandlerInvoke(bean, method, aClass));
                    totalHandlerMapSize++;
                }

            }
        }

        logger.info("初始化handlerMap完毕,总共加载了[{}]条数据", totalHandlerMapSize);
    }

    // 静态资源目的地加载
    private static void initResourceDefinition() {
        StorageManager storageManager = SpringContext.getStorageManager();

        List<Class<?>> simpleClasses = beanClasses.stream()
            .filter(aClass -> aClass.getAnnotation(MiniResource.class) != null).collect(Collectors.toList());

        simpleClasses.forEach(aClass -> {
            ResourceDefinition definition = new ResourceDefinition(aClass, true);
            storageManager.registerDefinition(aClass, definition);
        });

        logger.info("初始化普通静态资源文件目的地完毕,加载了[{}]条注解数据", storageManager.getDefinitionMap().size());

        ResourceDefinition definition = new ResourceDefinition(IMap.class, false);
        storageManager.registerMapResourceDefinition(IMap.class, definition);
        logger.info("初始化地图静态资源文件目的地完毕,加载了[{}]条注解数据", storageManager.getMapResourceDefinitionMap().size());
    }

    // 这里地图资源写死用IMap.class做key
    private static void initMapCsvCaches() {
        StorageManager storageManager = SpringContext.getStorageManager();

        Map<Class<? extends IMap>, ResourceDefinition> mapResourceDefinitionMap =
            storageManager.getMapResourceDefinitionMap();

        Map<Class<?>, ResourceDefinition> tempMap = new HashMap<>();

        Iterator<Map.Entry<Class<? extends IMap>, ResourceDefinition>> iterator =
            mapResourceDefinitionMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Class<? extends IMap>, ResourceDefinition> next = iterator.next();
            ResourceDefinition value = next.getValue();
            tempMap.put(IMap.class, value);
            break;
        }

        registerCsvCaches(storageManager, tempMap);
    }

    private static void initCsvCache() {
        StorageManager storageManager = SpringContext.getStorageManager();
        Map<Class<?>, ResourceDefinition> definitionMap = storageManager.getDefinitionMap();

        registerCsvCaches(storageManager, definitionMap);
    }

    private static void initStaticResource() {
        SpringContext.getStorageManager().initStorageMap();
    }

    private static void initMapResourceManager() {
        StorageManager storageManager = SpringContext.getStorageManager();

        InputStream cache = storageManager.getCache(IMap.class);
        SceneMapManager sceneMapManager = CONTEXT.getBean(SceneMapManager.class);

        CSVParser parser = SimpleUtil.getParserFromStream(cache);
        List<String> fieldNames = null;
        List<String> fieldType = null;
        List<CSVRecord> records = null;
        try {
            records = parser.getRecords();
            fieldNames = SimpleUtil.toListFromIterator(records.get(0).iterator());
            fieldType = SimpleUtil.toListFromIterator(records.get(1).iterator());
        } catch (Exception e) {
            e.printStackTrace();
        }

        records.stream().skip(2).forEach(record -> {
            String clazzName = record.get(1);
            List<String> fieldTypes = SimpleUtil.toListFromIterator(record.iterator());
            Class aClass = null;
            Object newInstance = null;
            try {
                aClass = Class.forName(clazzName);
                newInstance = aClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Field[] fields = aClass.getSuperclass().getDeclaredFields();
            List<Field> collectFields = Arrays.asList(fields).stream()
                .filter(field -> SimpleUtil.isSimpleClazz(field.getType())).collect(Collectors.toList());

            List<String> collectValues = Arrays.asList(record.get(0), record.get(3), record.get(4));

            ClassUtil.insertFields(newInstance, collectFields, collectValues);

            IMap instance = (IMap)newInstance;

            instance.init(SimpleUtil.toListFromIterator(record.iterator()));
            instance.initMapCreature();
            sceneMapManager.addMap(instance.getCurrentMapId(), instance);
        });

    }

    private static void registerCsvCaches(StorageManager storageManager,
        Map<Class<?>, ResourceDefinition> mapResourceDefinitionMap) {
        mapResourceDefinitionMap.forEach((aClass, resourceDefinition) -> {
            String location = resourceDefinition.getLocation();

            InputStream inputStream = SimpleUtil.getInputStreamFromFile(location);
            storageManager.registerCsvCache(location, inputStream);

            logger.info("初始化csv文件流结束,csv文件个数[{}],最终加载了[{}]条数据", mapResourceDefinitionMap.size(),
                storageManager.getCaches().size());
        });
    }
}
