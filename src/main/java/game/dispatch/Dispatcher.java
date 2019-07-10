package game.dispatch;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ebus.anno.EventReceiver;
import ebus.model.IEvent;
import ebus.service.EventBus;
import game.base.executor.account.IAccountExecutorService;
import game.dispatch.anno.HandlerAnno;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserRegister;
import net.model.USession;
import spring.SpringContext;
import utils.ClassUtil;
import utils.SimpleUtil;

/**
 * 应用层派发器
 *
 * @author : ddv
 * @since : 2019/4/28 下午9:05
 */

@Component
public class Dispatcher {

    private static Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private static Map<Class<?>, HandlerInvoke> handlerDestinationMap = new HashMap<>();

    @Autowired
    private IAccountExecutorService accountExecutorService;

    private static void addHandlerDestination(Class<?> clazz, HandlerInvoke handlerInvoke) {
        if (handlerDestinationMap.containsKey(clazz)) {
            logger.error("重复添加的clazz{}", clazz.toString());
            throw new RuntimeException("初始化应用层派发器出错!");
        }

        handlerDestinationMap.put(clazz, handlerInvoke);
    }

    // 初始化逻辑
    public void init() {
        ApplicationContext context = SpringContext.getApplicationContext();
        EventBus eventBus = context.getBean(EventBus.class);

        int dispatchHandlerMapSize = 0;
        int eventBusHandlerMapSize = 0;

        for (String name : context.getBeanDefinitionNames()) {
            Object bean = context.getBean(name);
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
                    addHandlerDestination(aClass, HandlerInvoke.createHandlerInvoke(bean, method, aClass));
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

    public void invoke(USession session, Object packet) {
        logger.info("请求类型[{}]", packet.getClass().getSimpleName());
        HandlerInvoke handlerInvoke = handlerDestinationMap.get(packet.getClass());
        if (handlerInvoke == null) {
            logger.error("协议[{}]未注册handlerMap,忽视此条消息!", packet.getClass());
            return;
        }

        // 取业务方法的第一个参数[session或者player]
        Object param = accountExecutorService.getParam(handlerInvoke, session);
        String accountId;
        if (packet.getClass() == CM_UserLogin.class || packet.getClass() == CM_UserRegister.class) {
            accountId = ClassUtil.getFieldByName(packet, "accountId", String.class);
        } else {
            accountId = SimpleUtil.getAccountIdFromSession(session);
        }
        int modIndex = accountExecutorService.modIndex(accountId);
        accountExecutorService.handle(handlerInvoke, param, modIndex, packet);
    }

}
