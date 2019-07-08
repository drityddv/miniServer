package game.user.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import db.middleware.AbstractEntity;

/**
 * @author : ddv
 * @since : 2019/5/5 上午10:47
 */

@Entity(name = "user")
public class UserEnt extends AbstractEntity<String> {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号Id' ", nullable = false)
    private String accountId;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '用户名' ", nullable = false)
    private String username;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号密码' ", nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '身份证号码' ", nullable = false)
    private String idCard;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '正式姓名' ", nullable = false)
    private String name;

    public static UserEnt valueOf(String accountId, String password, String username, String name, String idCard) {
        UserEnt userEnt = new UserEnt();
        userEnt.accountId = accountId;
        userEnt.username = username;
        userEnt.password = password;
        userEnt.idCard = idCard;
        userEnt.name = name;
        return userEnt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void doSerialize() {

    }

    @Override
    public void doDeserialize() {

    }

    @Override
    public String getId() {
        return this.accountId;
    }

    @Override
    public String toString() {
        return "UserEnt{" + "accountId='" + accountId + '\'' + ", username='" + username + '\'' + ", password='"
            + password + '\'' + ", idCard=" + idCard + ", name='" + name + '\'' + '}';
    }
}
