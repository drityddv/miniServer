package game.common.packet;

/**
 * id: -1
 *
 * @author : ddv
 * @since : 2019/5/6 下午12:26
 */

public class SM_Message {

	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static SM_Message valueOf(int id) {
		SM_Message sm = new SM_Message();
		sm.id = id;
		return sm;
	}

	@Override
	public String toString() {
		return "SM_Message{" +
				"id=" + id +
				'}';
	}
}
