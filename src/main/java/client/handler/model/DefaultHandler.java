package client.handler.model;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import client.MessageEnum;
import client.anno.Action;
import client.handler.IHandler;
import game.base.message.packet.SM_Message;
import utils.ClassUtil;

/**
 * 默认处理器 找不到处理器调用这个
 *
 * @author : ddv
 * @since : 2019/7/10 下午10:19
 */

public class DefaultHandler implements IHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultHandler.class);

    @Override
    public void handler(Object object) {
        Method method = ClassUtil.getMethodByAnnotation(object, Action.class);
        if (method != null) {
            ReflectionUtils.invokeMethod(method, object);
            return;
        }
        if (object instanceof SM_Message) {
            SM_Message sm = (SM_Message)object;
            MessageEnum messageEnum = MessageEnum.getById(sm.getI18nId());
            logger.info("操作提示:[{}]", messageEnum.getMessage());
            return;
        }
        logger.info("服务端发包:[{}]", object.toString());
    }
}
