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
		return "CM_UserRegister{" +
				"accountId='" + accountId + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
