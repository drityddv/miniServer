package spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    }

    private SpringController() {}

    private static void initBeanClasses() {
        beanClasses = new ArrayList<>();
        beanNames.forEach(beanName -> {
            beanClasses.add(CONTEXT.getBean(beanName).getClass());
        });
    }

    public static List<Class<?>> getBeanClasses() {
        return beanClasses;
    }

    public static SpringController getInstance() {
        return instance;
    }

    public static ApplicationContext getContext() {
        return CONTEXT;
    }

    public static void init() {
        logger.info("spring初始化完成...");
    }

}
