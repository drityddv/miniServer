package middleware.model;

import java.util.List;

/**
 * @author : ddv
 * @since : 2019/4/28 上午9:45
 */
@SuppressWarnings("all")
public class SM_Success {

	private String username;
	private int stateCode;
	private List<Integer> tokenList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public List<Integer> getTokenList() {
		return tokenList;
	}

	public void setTokenList(List<Integer> tokenList) {
		this.tokenList = tokenList;
	}

	@Override
	public String toString() {
		return "SM_Success{" +
				"username='" + username + '\'' +
				", stateCode=" + stateCode +
				", tokenList=" + tokenList +
				'}';
	}
}
