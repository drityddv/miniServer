package spring;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import middleware.anno.HandlerAnno;
import middleware.anno.Manager;
import middleware.anno.Resource;
import middleware.dispatch.Dispatcher;
import middleware.dispatch.HandlerInvoke;
import middleware.resource.IManager;
import middleware.resource.middle.ResourceDefinition;
import middleware.resource.storage.StorageManager;
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
        initCsvCache();
        initStaticResource();
		initResourceManager();
    }

    private static void initBeanClasses() {
        beanClasses = new ArrayList<>();
        beanNames.forEach(beanName -> {
            beanClasses.add(CONTEXT.getBean(beanName).getClass());
        });
    }

    private SpringController() {}

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

                // HandlerAnno注解处理
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

        List<Class<?>> classList = beanClasses.stream().filter(aClass -> aClass.getAnnotation(Resource.class) != null)
            .collect(Collectors.toList());

        classList.forEach(beanClass -> {
            ResourceDefinition definition = new ResourceDefinition(beanClass);
            storageManager.registerDefinition(beanClass, definition);
        });

        logger.info("初始化静态资源文件目的地结束,文件数组长度[{}],最终加载了[{}]条数据", classList.size(),
            storageManager.getDefinitionMap().size());
    }

    private static void initCsvCache() {
        StorageManager storageManager = SpringContext.getStorageManager();
        Map<Class<?>, ResourceDefinition> definitionMap = storageManager.getDefinitionMap();

        definitionMap.forEach((aClass, resourceDefinition) -> {
            String location = resourceDefinition.getLocation();

            InputStream inputStream = SimpleUtil.getInputStreamFromFile(location);
            storageManager.registerCsvCache(location, inputStream);

            logger.info("初始化csv文件流结束,csv文件个数[{}],最终加载了[{}]条数据", definitionMap.size(),
                storageManager.getCaches().size());
        });

    }

    private static void initStaticResource() {
        SpringContext.getStorageManager().initStorageMap();
    }

    private static void initResourceManager() {
        List<Class<?>> classList = beanClasses.stream().filter(aClass -> aClass.getAnnotation(Manager.class) != null)
            .collect(Collectors.toList());

        classList.forEach(aClass -> {
            IManager manager = (IManager)CONTEXT.getBean(aClass);
			manager.initManager();
        });
    }
}
