package middleware.resource.storage;

/**
 * @author : ddv
 * @since : 2019/5/13 下午2:07
 */

public class StorageLong<V> extends Storage<Long, V> {

    public V get(long k) {
        return storageGet(k);
    }

    public void put(long k, V v) {
        storageAdd(k, v);
    }
}
