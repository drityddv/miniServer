package middleware.dispatch;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.service.IMiniExecutorService;
import game.user.login.packet.CM_UserLogin;
import middleware.manager.ClazzManager;
import net.model.USession;

/**
 * 应用层请求派发器
 *
 * @author : ddv
 * @since : 2019/4/28 下午9:05
 */

@Component
public class Dispatcher {

    private static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    @Autowired
    private IMiniExecutorService miniExecutorService;

    /**
     * 方法invoke对应表
     */
    private Map<Class<?>, HandlerInvoke> handlerDestinationMap = new HashMap<>();

    public Map<Class<?>, HandlerInvoke> getHandlerDestinationMap() {
        return handlerDestinationMap;
    }

    public void addHandlerDestination(Class<?> clazz, HandlerInvoke handlerInvoke) {
        if (handlerDestinationMap.containsKey(clazz)) {
            logger.error("重复添加的clazz{}", clazz.toString());
            throw new RuntimeException("初始化应用层派发器出错!");
        }

        handlerDestinationMap.put(clazz, handlerInvoke);
    }

    public void invoke(USession session, Object packet) {
        logger.info("invoke [{}]", packet.getClass());

        HandlerInvoke handlerInvoke = handlerDestinationMap.get(packet.getClass());

        if (handlerInvoke == null) {
            logger.error("协议[{}]未注册handlerMap,忽视此条消息!", packet.getClass());
            return;
        }

        // 登陆走net线程
        if (packet.getClass() == CM_UserLogin.class) {
            handlerInvoke.invoke(session, packet);
            return;
        }

        miniExecutorService.handle(handlerInvoke, session, packet, ClazzManager.getIdByClazz(packet.getClass()));

    }
}
