package db.middleware;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

/**
 * @author : ddv
 * @since : 2019/5/5 下午3:28
 */
public class HibernateUtil<K extends Serializable & Comparable<K>, T extends IEntity<K>> implements IOrmTemplate<K, T> {

	private static SessionFactory sessionFactory;

	static {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	@Override
	public void save(IEntity object) {
		Session session = sessionFactory.getCurrentSession();
		session.getTransaction().begin();
		object.serialize();
		object.setTimeStamp();
		session.save(object);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public T loadOrCreate(Class<T> entityType, K id, EntityBuilder<K, T> builder) {
		Session session = sessionFactory.getCurrentSession();
		session.getTransaction().begin();

		T object = session.get(entityType, id);

		session.getTransaction().commit();

		if (object == null) {
			object = builder.newInstance(id);
			object.serialize();
			save(object);
			return object;
		}

		object.unSerialize();
		session.close();
		return object;
	}

	@Override
	public T load(Class<T> entityType, K id) {
		Session session = sessionFactory.getCurrentSession();
		session.getTransaction().begin();

		T object = session.get(entityType, id);
		session.getTransaction().commit();
		session.close();
		if (object != null) {
			object.unSerialize();
		}

		return object;
	}
}
