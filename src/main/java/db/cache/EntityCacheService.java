package db.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.middleware.EntityBuilder;
import db.middleware.IEntity;
import db.middleware.IOrmTemplate;

/**
 * @author : ddv
 * @since : 2019/5/16 下午4:19
 */
@Component
public class EntityCacheService<K extends Serializable & Comparable<K>, T extends IEntity<K>>
    implements IEntityCacheService<K, T> {

    @Autowired
    private IOrmTemplate<K, T> ormTemplate;

    private Map<Class<?>, EntityCache<K, T>> entityCacheMap = new ConcurrentHashMap<>();

    private EntityCache<K, T> getEntityCache(Class<?> clazz) {
        return entityCacheMap.get(clazz);
    }

    @Override
    public T loadOrCreate(Class<T> entityType, K id, EntityBuilder<K, T> builder) {
        if (!entityCacheMap.containsKey(entityType)) {
            entityCacheMap.put(entityType, new EntityCache<>());
        }
        EntityCache<K, T> entityCache = entityCacheMap.get(entityType);
        if (entityCache == null) {
            entityCache = entityCacheMap.putIfAbsent(entityType, new EntityCache<>());
        }
        T t = null;
        if (!entityCache.inCache(id)) {
            t = ormTemplate.loadOrCreate(entityType, id, builder);
            entityCache.put(id, t);
        }
        return t;
    }

    @Override
    public T load(Class<T> entityType, K id) {
        if (!entityCacheMap.containsKey(entityType)) {
            entityCacheMap.put(entityType, new EntityCache<>());
        }

        EntityCache<K, T> entityCache = entityCacheMap.putIfAbsent(entityType, new EntityCache<>());

        T t = null;
        if (!entityCache.inCache(id)) {
            t = ormTemplate.load(entityType, id);
            entityCache.put(id, t);
        } else {
            t = entityCache.get(id);
        }
        return t;
    }

    @Override
    public void save(T object) {
        if (object == null) {
            throw new RuntimeException("cache object has been deleted");
        }

        if (!entityCacheMap.containsKey(object.getClass())) {
            entityCacheMap.put(object.getClass(), new EntityCache<>());
        }

        EntityCache<K, T> entityCache = entityCacheMap.get(object.getClass());
        if (entityCache == null) {
            entityCache = entityCacheMap.putIfAbsent(object.getClass(), new EntityCache<>());
        }

        ormTemplate.save(object);
        entityCache.put(object.getId(), object);
    }
}
