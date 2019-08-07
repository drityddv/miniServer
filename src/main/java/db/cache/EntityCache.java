package db.cache;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import db.model.IEntity;

/**
 *
 * @author : ddv
 * @since : 2019/5/16 上午11:30
 */

public class EntityCache<K extends Serializable & Comparable<K>, T extends IEntity<K>> {

    private Map<K, T> cache = Collections.synchronizedMap(new WeakHashMap<>());

    public boolean inCache(K k) {
        return cache.containsKey(k);
    }

    public T get(K k) {
        return cache.get(k);
    }

    public void put(K k, T t) {
        cache.put(k, t);
    }

    public void clear() {
        cache.clear();
    }

    public Map<K, T> getCache() {
        return cache;
    }
}
