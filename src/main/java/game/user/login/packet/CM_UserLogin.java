package game.user.login.packet;

/**
 * id:1
 * @author : ddv
 * @since : 2019/4/28 下午8:15
 */

public class CM_UserLogin {

	private String accountId;
	private String password;

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

	@Override
	public String toString() {
		return "CM_UserLogin{" +
				"accountId='" + accountId + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
