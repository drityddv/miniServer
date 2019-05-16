package game.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import db.middleware.EntityBuilder;
import db.middleware.IOrmTemplate;
import game.user.login.entity.UserEnt;

/**
 * 静态资源,持久组件
 *
 * @author : ddv
 * @since : 2019/5/5 下午4:53
 */
@Component
public class LoginManager {

    @Autowired
    private EntityCacheService<String, UserEnt> entityCacheService;

    @Autowired
    private IOrmTemplate<String, UserEnt> userHibernateUtil;
    // private IOrmTemplate<String, UserEnt> userHibernateUtil = new HibernateUtil<>();

    public UserEnt loadOrCreate(String accountId) {
        // return userHibernateUtil.loadOrCreate(UserEnt.class, accountId, new EntityBuilder<String, UserEnt>() {
        // @Override
        // public UserEnt newInstance(String accountId) {
        // return UserEnt.valueOf(accountId);
        // }
        // });

        return entityCacheService.loadOrCreate(UserEnt.class, accountId, new EntityBuilder<String, UserEnt>() {
            @Override
            public UserEnt newInstance(String accountId) {
                return UserEnt.valueOf(accountId);
            }
        });
    }

    public UserEnt load(String accountId) {
        return entityCacheService.load(UserEnt.class, accountId);
    }

    public void save(String accountId) {
        userHibernateUtil.save(loadOrCreate(accountId));
    }

    public void saveEntity(UserEnt userEnt) {
        userHibernateUtil.save(userEnt);
    }

}
