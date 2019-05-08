package game.user.login.entity;

import java.util.Arrays;

import javax.persistence.*;

import db.middleware.AbstractEntity;
import game.user.login.model.Person;
import net.utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/5/5 上午10:47
 */

@Entity(name = "user")
public class UserEnt extends AbstractEntity<String> {

    @Transient
    private Person person;
    @Lob
    @Column(columnDefinition = "blob comment '个人身份信息' ")
    private byte[] personData;

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号Id' ", nullable = false)
    private String accountId;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '用户名' ", nullable = false)
    private String username;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号密码' ", nullable = false)
    private String password;

    public static UserEnt valueOf(String accountId) {
        UserEnt userEnt = new UserEnt();
        userEnt.accountId = accountId;
        userEnt.person = Person.valueOf();
        return userEnt;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public byte[] getPersonData() {
        return personData;
    }

    public void setPersonData(byte[] personData) {
        this.personData = personData;
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
        if (this.person != null) {
            this.personData = ProtoStuffUtil.serialize(this.person);
        }
    }

    @Override
    public void doDeserialize() {
        if (this.personData != null) {
            this.person = ProtoStuffUtil.deserialize(this.personData, Person.class);
        }
    }

    @Override
    public String getId() {
        return this.accountId;
    }

    @Override
    public String toString() {
        return "UserEnt{" + "person=" + person + ", personData=" + Arrays.toString(personData) + ", accountId='"
            + accountId + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }

}
