package client.handler.model;

import java.util.HashMap;
import java.util.Map;

import client.handler.IHandler;

/**
 * @author : ddv
 * @since : 2019/7/10 下午10:47
 */

public enum HandlerEnum {
    // 登陆成功
    LOGIN_SUCCESS(3, LoginHandler.class),
    // 背包信息
    PACK_INFO(91, PackHandler.class),
    // 装备栏信息
    STORAGE(141, StorageHandler.class),;

    public static Map<Integer, IHandler> handlerMap = new HashMap<>();

    static {
        for (HandlerEnum handlerEnum : HandlerEnum.values()) {
            try {
                handlerMap.put(handlerEnum.id, handlerEnum.handlerClazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int id;
    private Class<? extends IHandler> handlerClazz;

    HandlerEnum(int id, Class<? extends IHandler> handlerClazz) {
        this.id = id;
        this.handlerClazz = handlerClazz;
    }

    public static IHandler getHandler(int id) {
        IHandler handler = handlerMap.get(id);
        if (handler == null) {
            return new DefaultHandler();
        }
        return handler;
    }
}
