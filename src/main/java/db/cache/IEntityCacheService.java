package db.cache;

import java.io.Serializable;

import db.middleware.EntityBuilder;
import db.middleware.IEntity;

/**
 * 缓存中间件
 *
 * @author : ddv
 * @since : 2019/5/16 下午4:41
 */

public interface IEntityCacheService<K extends Serializable & Comparable<K>, T extends IEntity<K>> {

    /**
     * 查找或创建
     *
     * @param entityType
     * @param id
     * @param builder
     * @return
     */
    T loadOrCreate(Class<T> entityType, K id, EntityBuilder<K, T> builder);

    /**
     * 查找 无则返回null
     *
     * @param entityType
     * @param id
     * @return
     */
    T load(Class<T> entityType, K id);

    /**
     * 持久化操作
     *
     * @param object
     */
    void save(T object);

}
