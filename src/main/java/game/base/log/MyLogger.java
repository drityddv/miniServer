package game.base.log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;


/**
 * @author : ddv
 * @since : 2020/3/5 3:44 PM
 */

public class MyLogger {

    public static Logger logger;

    public static final String FQCN = MyLogger.class.getName();

    public static MyLogger valueOf(Class clazz) {
        MyLogger myLogger = new MyLogger();
        myLogger.logger = LoggerFactory.getLogger(clazz);
        return myLogger;
    }

    public void info(String content, Object... args) {
        logger.info(content, args);
    }


	static {

	}
	static {
        try {
            Enhancer eh = new Enhancer();
            eh.setSuperclass(org.apache.log4j.Logger.class);
            eh.setCallbackType(LogInterceptor.class);
            Class c = eh.createClass();
            Enhancer.registerCallbacks(c, new LogInterceptor[] {new LogInterceptor()});

            Constructor<org.apache.log4j.Logger> constructor = c.getConstructor(String.class);
            org.apache.log4j.Logger loggerProxy = constructor.newInstance(Logger.class.getName());

            LoggerRepository loggerRepository = LogManager.getLoggerRepository();
            org.apache.log4j.spi.LoggerFactory lf = ReflectionUtil.getFieldValue(loggerRepository, "defaultFactory");
            Object loggerFactoryProxy = Proxy.newProxyInstance(LoggerFactory.class.getClassLoader(),
                new Class[] {org.apache.log4j.spi.LoggerFactory.class}, new NewLoggerHandler(loggerProxy));

            ReflectionUtil.setFieldValue(loggerRepository, "defaultFactory", loggerFactoryProxy);
            logger = org.slf4j.LoggerFactory.getLogger(Logger.class.getName());
            ReflectionUtil.setFieldValue(loggerRepository, "defaultFactory", lf);


        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException
            | InstantiationException e) {
            throw new RuntimeException("初始化Logger失败", e);
        }


    }

}
