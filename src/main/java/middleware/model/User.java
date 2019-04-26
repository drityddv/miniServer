package middleware.model;

import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/4/26 下午4:42
 */

public class User {

	private int uid;
	private String accountId;
	private String username;
	private String password;
	private Map<String,String> map;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "User{" +
				"uid=" + uid +
				", accountId='" + accountId + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", map=" + map +
				'}';
	}
}
