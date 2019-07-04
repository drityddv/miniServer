package spring;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import game.base.ebus.EventBus;
import game.base.ebus.IEvent;
import middleware.anno.EventReceiver;
import middleware.anno.HandlerAnno;
import middleware.anno.MiniResource;
import middleware.anno.Static;
import middleware.dispatch.Dispatcher;
import middleware.dispatch.HandlerInvoke;
import middleware.resource.middle.ResourceDefinition;
import middleware.resource.storage.Storage;
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
        // initMapCsvCaches();
        initCsvCache();
        initStaticResource();
        // initMapResourceManager();
        initManagerStorage();
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
     * 初始化派发器和eventBus
     */
    private static void initHandlerDestinationMap() {
        Dispatcher dispatcher = CONTEXT.getBean(Dispatcher.class);
        EventBus eventBus = CONTEXT.getBean(EventBus.class);

        int dispatchHandlerMapSize = 0;
        int eventBusHandlerMapSize = 0;

        for (String name : beanNames) {
            Object bean = CONTEXT.getBean(name);
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();

            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                // gateway注解
                if (method.isAnnotationPresent(HandlerAnno.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    if (parameterTypes == null || parameterTypes.length != 2) {
                        logger.error("HandlerAnno注解处理出错,{}方法有误!", method);
                        throw new RuntimeException("facade方法参数定义有误!");
                    }

                    Class<?> aClass = parameterTypes[1];

                    dispatcher.addHandlerDestination(aClass, HandlerInvoke.createHandlerInvoke(bean, method, aClass));
                    dispatchHandlerMapSize++;

                }
                // 事件消费者注解
                else if (method.isAnnotationPresent(EventReceiver.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes == null || parameterTypes.length != 1) {
                        logger.error("EventReceiver注解处理出错,{}方法有误!", method);
                        throw new RuntimeException("facade方法参数定义有误!");
                    }

                    Class<? extends IEvent> aClass = (Class<? extends IEvent>)parameterTypes[0];
                    eventBus.addHandlerDestination(aClass, HandlerInvoke.createHandlerInvoke(bean, method, aClass));
                    eventBusHandlerMapSize++;
                }

            }
        }

        logger.info("初始化handlerMap完毕,总共加载了[{}]条数据", dispatchHandlerMapSize);
        logger.info("初始化eventBus消费者完毕,总共加载了[{}]条数据", eventBusHandlerMapSize);
    }

    // 静态资源目的地加载
    private static void initResourceDefinition() {
        StorageManager storageManager = SpringContext.getStorageManager();

        // 加载普通静态资源
        List<Class<?>> simpleClasses = beanClasses.stream()
            .filter(aClass -> aClass.getAnnotation(MiniResource.class) != null).collect(Collectors.toList());

        simpleClasses.forEach(aClass -> {
            ResourceDefinition definition = new ResourceDefinition(aClass, true);
            storageManager.registerDefinition(aClass, definition);
        });

        logger.info("初始化普通静态资源文件目的地完毕,加载了[{}]条注解数据", storageManager.getDefinitionMap().size());
    }

    // 普通静态资源csv加载
    private static void initCsvCache() {
        StorageManager storageManager = SpringContext.getStorageManager();
        Map<Class<?>, ResourceDefinition> definitionMap = storageManager.getDefinitionMap();

        registerCsvCaches(storageManager, definitionMap);
    }

    private static void initStaticResource() {
        SpringContext.getStorageManager().initSimpleResource();
    }

    private static void registerCsvCaches(StorageManager storageManager,
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
    private static void initManagerStorage() {
        beanClasses.forEach(aClass -> {
            Object contextBean = CONTEXT.getBean(aClass);
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

}
