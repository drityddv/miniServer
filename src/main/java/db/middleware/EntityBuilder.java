package db.middleware;

import java.io.Serializable;

/**
 * @author : ddv
 * @since : 2019/5/6 上午11:13
 */

public interface EntityBuilder<K extends Comparable<K> & Serializable, T extends IEntity<K>> {

	/**
	 * create逻辑
	 *
	 * @param id
	 * @return
	 */
	T newInstance(K id);
}
