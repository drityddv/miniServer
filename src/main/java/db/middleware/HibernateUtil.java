package db.middleware;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : ddv
 * @since : 2019/5/5 下午3:28
 */
@Transactional(rollbackFor = {})
@Component
public class HibernateUtil<K extends Serializable & Comparable<K>, T extends IEntity<K>> implements IOrmTemplate<K, T> {

    @Resource
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(IEntity object) {
        Session session = getSession();
        object.serialize();
        object.setTimeStamp();
        session.save(object);
    }

    @Override
    public T loadOrCreate(Class<T> entityType, K id, EntityBuilder<K, T> builder) {
        Session session = getSession();

        T object = session.get(entityType, id);

        if (object == null) {
            object = builder.newInstance(id);
            object.serialize();
            save(object);
            return object;
        }

        object.unSerialize();
        return object;
    }

    @Override
    public T load(Class<T> entityType, K id) {
        Session session = getSession();

        T object = session.get(entityType, id);
        if (object != null) {
            object.unSerialize();
        }

        return object;
    }
}
