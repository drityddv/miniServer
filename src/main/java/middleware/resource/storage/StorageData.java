package middleware.resource.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/5/10 下午6:27
 */

public class StorageData<K, V> {

    private Map<K, V> values = new HashMap<>();

    public Map<K, V> getValues() {
        return values;
    }

    public void setValues(Map<K, V> values) {
        this.values = values;
    }
}
