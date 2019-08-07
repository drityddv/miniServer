package db.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.model.EntityBuilder;
import db.model.IEntity;
import db.model.IOrmTemplate;

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

    @Override
    public T loadOrCreate(Class<T> entityType, K id, EntityBuilder<K, T> builder) {
        checkEntityCacheMap(entityCacheMap, entityType);
        EntityCache<K, T> entityCache = entityCacheMap.putIfAbsent(entityType, new EntityCache<>());

        T t = entityCache.get(id);
        if (t == null) {
            t = ormTemplate.loadOrCreate(entityType, id, builder);
            entityCache.put(id, t);
        }
        save(t);
        return t;
    }

    @Override
    public T load(Class<T> entityType, K id) {

        checkEntityCacheMap(entityCacheMap, entityType);

        EntityCache<K, T> entityCache = entityCacheMap.putIfAbsent(entityType, new EntityCache<>());

        T t = entityCache.get(id);

        if (t == null) {
            t = ormTemplate.load(entityType, id);
            if (t == null) {
                return t;
            }
            entityCache.put(id, t);
        }
        return t;
    }

    @Override
    public void save(T object) {
        if (object == null) {
            throw new RuntimeException("cache object has been deleted");
        }

        checkEntityCacheMap(entityCacheMap, (Class<T>)object.getClass());
        EntityCache<K, T> entityCache = entityCacheMap.putIfAbsent(object.getClass(), new EntityCache<>());

        ormTemplate.save(object);
        entityCache.put(object.getId(), object);
    }

    private void checkEntityCacheMap(Map<Class<?>, EntityCache<K, T>> entityCacheMap, Class<T> entityType) {
        if (!entityCacheMap.containsKey(entityType)) {
            entityCacheMap.put(entityType, new EntityCache<>());
        }
    }

	public Map<Class<?>, EntityCache<K, T>> getEntityCacheMap() {
		return entityCacheMap;
	}
}
