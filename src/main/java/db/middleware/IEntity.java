package db.middleware;

import java.io.Serializable;

/**
 * @author : ddv
 * @since : 2019/5/5 下午2:24
 */

public interface IEntity<T extends Serializable & Comparable<T>> {

    /**
     * 获取实体主键
     *
     * @return
     */
    T getId();

    /**
     * 序列化逻辑
     */
    void serialize();

    /**
     * 反序列化逻辑
     */
    void unSerialize();

    /**
     * 处理时间戳
     */
    void setTimeStamp();

}
