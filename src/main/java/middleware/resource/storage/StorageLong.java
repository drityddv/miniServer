package middleware.resource.storage;

import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/5/13 下午2:07
 */

public class StorageLong<V> extends Storage<Long, V> {

    public V get(long k) {
        return getFromStorageMap(k);
    }

    public void put(long k, V v) {
        addIntoStorageMap(k, v);
    }

    public Map<Long, V> getStorageData() {
        return getStorageMap();
    }
}
