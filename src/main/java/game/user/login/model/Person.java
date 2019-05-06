package game.user.login.model;

/**
 * @author : ddv
 * @since : 2019/5/5 上午11:54
 */

public class Person {

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 真实姓名
	 */
	private String name;

	public static Person valueOf() {
		return new Person();
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
		return "Person{" +
				"idCard='" + idCard + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
