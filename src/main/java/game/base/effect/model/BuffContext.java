package game.base.effect.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/24 10:52 AM
 */

public class BuffContext {

    private Map<BuffContextParamEnum, Object> contextMap = new HashMap<>();

    public static BuffContext valueOf() {
        BuffContext context = new BuffContext();
        return context;
    }

    public static BuffContext valueOf(BuffContext template) {
        BuffContext context = new BuffContext();
        template.contextMap.forEach((paramKey, o) -> {
            context.contextMap.put(paramKey, o);
        });
        return context;
    }

    public void addParam(BuffContextParamEnum key, Object object) {
        contextMap.put(key, object);
    }

    public <T> T getParam(BuffContextParamEnum key) {
        return (T)contextMap.get(key);
    }

}
