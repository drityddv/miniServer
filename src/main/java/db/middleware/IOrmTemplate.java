package db.middleware;

import java.io.Serializable;

/**
 * 持久层接口 由框架自行实现
 *
 * @author : ddv
 * @since : 2019/5/6 上午10:32
 */

public interface IOrmTemplate<K extends Serializable & Comparable<K>, T extends IEntity<K>> {

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
