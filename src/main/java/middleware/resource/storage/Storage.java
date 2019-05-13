package middleware.resource.storage;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/5/10 下午8:13
 */

public class Storage<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private Map<K, V> data = new HashMap<>();

    public void storageAdd(K k, V v) {
        data.put(k, v);
    }

    public V storageGet(K k) {
        return data.get(k);
    }
}
