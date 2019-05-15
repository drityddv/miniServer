package db.middleware;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/5/5 下午2:28
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable & Comparable<T>> implements IEntity<T> {

    @Column(columnDefinition = "bigint default 0 comment '更新时间戳' ")
    private long updatedAt;

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public void serialize() {
        this.updatedAt = TimeUtil.now() / 1000;
        doSerialize();
    }

    @Override
    public void unSerialize() {
        doDeserialize();
    }

    @Override
    public void setTimeStamp() {
        this.updatedAt = TimeUtil.now() / 1000;
    }

    /**
     * 序列化
     */
    public abstract void doSerialize();

    /**
     * 反序列化
     */
    public abstract void doDeserialize();
}
