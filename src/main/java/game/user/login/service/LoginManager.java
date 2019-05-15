package game.user.login.service;

import db.middleware.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.middleware.EntityBuilder;
import db.middleware.IOrmTemplate;
import game.user.login.entity.UserEnt;
import spring.SpringContext;
import spring.SpringController;

/**
 * 静态资源,持久组件
 *
 * @author : ddv
 * @since : 2019/5/5 下午4:53
 */
@Component
public class LoginManager {

    private IOrmTemplate<String, UserEnt> userHibernateUtil = new HibernateUtil<>();

    public UserEnt loadOrCreate(String accountId) {
        return userHibernateUtil.loadOrCreate(UserEnt.class, accountId, new EntityBuilder<String, UserEnt>() {
            @Override
            public UserEnt newInstance(String accountId) {
                return UserEnt.valueOf(accountId);
            }
        });
    }

    public UserEnt load(String accountId) {
        return userHibernateUtil.load(UserEnt.class, accountId);
    }

    public void save(String accountId) {
        userHibernateUtil.save(loadOrCreate(accountId));
    }

    public void saveEntity(UserEnt userEnt) {
        userHibernateUtil.save(userEnt);
    }

}
