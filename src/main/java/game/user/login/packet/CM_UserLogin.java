package game.user.login.packet;

/**
 * id:1
 * @author : ddv
 * @since : 2019/4/28 下午8:15
 */

public class CM_UserLogin {

	private String username;
	private String password;

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
	public String toString() {
		return "CM_UserLogin{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
