package ebus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import ebus.model.IEvent;
import game.dispatch.HandlerInvoke;

/**
 * 事件组件
 *
 * @author : ddv
 * @since : 2019/6/22 下午7:23
 */
@Component
public class EventBus {

    private static Logger logger = LoggerFactory.getLogger(EventBus.class);

    private Map<Class<? extends IEvent>, List<HandlerInvoke>> receiverList = new HashMap<>();

    public void addHandlerDestination(Class<? extends IEvent> clazz, HandlerInvoke handlerInvoke) {

        if (!receiverList.containsKey(clazz)) {
            receiverList.put(clazz, new ArrayList<>());
        }

        List<HandlerInvoke> invokeList = receiverList.get(clazz);

        // 目前不支持一个service中重复监听同一种事件
        for (HandlerInvoke invoke : invokeList) {
            if (invoke.getBean().equals(handlerInvoke.getBean())) {
                logger.error("重复的消费行为注册，bean[{}]", invoke.getBean());
                throw new RuntimeException("eventBus init error");
            }
        }

        invokeList.add(handlerInvoke);
    }

    public void pushEventSyn(IEvent event) {
        Class<? extends IEvent> eventClass = event.getClass();
        List<HandlerInvoke> invokeList = receiverList.get(eventClass);
        if (!CollectionUtils.isEmpty(invokeList)) {
            invokeList.forEach(handlerInvoke -> {
                handlerInvoke.invoke(event);
            });
        }

    }
}
