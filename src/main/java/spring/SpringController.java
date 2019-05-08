package spring;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import middleware.anno.HandlerAnno;
import middleware.dispatch.Dispatcher;
import middleware.dispatch.HandlerInvoke;

/**
 * @author : ddv
 * @since : 2019/4/28 下午3:28
 */

public class SpringController {

    private static final Logger logger = LoggerFactory.getLogger(SpringController.class);

    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");

    private static SpringController instance = new SpringController();

    private SpringController() {}

    public static SpringController getInstance() {
        return instance;
    }

    public static ApplicationContext getContext() {
        return CONTEXT;
    }

    /**
     * 初始化handlerMap
     */
    public static void initHandlerDestinationMap() {
        String[] names = CONTEXT.getBeanDefinitionNames();
        Dispatcher dispatcher = CONTEXT.getBean(Dispatcher.class);
        int totalHandlerMapSize = 0;

        for (String name : names) {
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
}
