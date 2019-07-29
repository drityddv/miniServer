package game.base.buff.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/29 10:41 AM
 */

public class BaseBuffConfig {
    private Map<BuffParamEnum, Object> params = new HashMap();

    public static BaseBuffConfig valueOf() {
        return new BaseBuffConfig();
    }

    public void addParam(BuffParamEnum key, Object object) {
        params.put(key, object);
    }

    public <T> T getParam(BuffParamEnum key) {
        return (T)params.get(key);
    }

}
