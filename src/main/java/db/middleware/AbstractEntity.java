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

	@Column(columnDefinition = "int default 0 comment '更新时间戳' ")
	private int updatedAt;

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(int updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public void serialize() {
		this.updatedAt = (int) TimeUtil.now();
		doSerialize();
	}

	@Override
	public void unSerialize() {
		doDeserialize();
	}

	@Override
	public void setTimeStamp() {
		this.updatedAt = (int) TimeUtil.now();
	}

	public abstract void doSerialize();

	public abstract void doDeserialize();
}
