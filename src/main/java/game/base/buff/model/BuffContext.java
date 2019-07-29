package game.base.buff.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/24 10:52 AM
 */

public class BuffContext {

    private Map<BuffParamEnum, Object> contextMap = new HashMap<>();

    public static BuffContext valueOf(BaseBuffConfig config) {
        BuffContext context = new BuffContext();
        config.getParams().forEach((buffParamEnum, o) -> {
            context.contextMap.put(buffParamEnum, o);
        });
        return context;
    }

    public void addParam(BuffParamEnum key, Object object) {
        contextMap.put(key, object);
    }

    public <T> T getParam(BuffParamEnum key) {
        return (T)contextMap.get(key);
    }

}
