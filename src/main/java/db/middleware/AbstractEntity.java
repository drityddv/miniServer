package db.middleware;

import utils.TimeUtil;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author : ddv
 * @since : 2019/5/5 下午2:28
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable & Comparable<T>> implements IEntity<T> {

	@Column(columnDefinition = "int default 0 comment '创建时间戳' ")
	private int createdAt;

	@Column(columnDefinition = "int default 0 comment '更新时间戳' ")
	private int updatedAt;

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	public int getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(int updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public void serialize() {
		if (this.createdAt == 0) {
			this.createdAt = (int) TimeUtil.now();
		}
		this.updatedAt = (int) TimeUtil.now();
		doSerialize();
	}

	@Override
	public void unSerialize() {
		doDeserialize();
	}

	public abstract void doSerialize();

	public abstract void doDeserialize();
}
