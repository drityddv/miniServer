package game.user.login.packet;

/**
 * id:4
 *
 * @author : ddv
 * @since : 2019/5/6 下午12:14
 */

public class CM_UserRegister {

    private String accountId;
    private String password;
    // 游戏账户昵称
    private String username;
    private String idCard;
    // 真实姓名
    private String name;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CM_UserRegister{" + "accountId='" + accountId + '\'' + ", password='" + password + '\'' + ", username='"
            + username + '\'' + ", idCard='" + idCard + '\'' + ", name='" + name + '\'' + '}';
    }
}
