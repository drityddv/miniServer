package db.cache;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import db.middleware.EntityBuilder;
import db.middleware.IEntity;
import db.middleware.IOrmTemplate;

/**
 * @author : ddv
 * @since : 2019/5/16 上午11:30
 */

public class EntityCache<K extends Serializable & Comparable<K>, T extends IEntity<K>> implements IOrmTemplate {

    private Map<K, T> cache = Collections.synchronizedMap(new WeakHashMap<>());;

    // public void init() {
    // cache = Collections.synchronizedMap(new WeakHashMap<>());
    // }

    @Override
    public IEntity loadOrCreate(Class entityType, Serializable id, EntityBuilder builder) {
        T t = cache.get(id);
        return t;
    }

    @Override
    public IEntity load(Class entityType, Serializable id) {
        return null;
    }

    @Override
    public void save(IEntity object) {

    }
}
