package game.base.map.base;

import utils.IdUtil;
import game.base.object.AbstractCreature;

/**
 * 地图中的生物单位
 *
 * @author : ddv
 * @since : 2019/5/6 下午9:26
 */

public class MapCreature extends AbstractCreature {

	private String accountId;
	private int x;
	private int y;

	public static MapCreature valueOf(String accountId, int x, int y) {
		MapCreature creature = new MapCreature();
		creature.setAccountId(accountId);
		creature.setX(x);
		creature.setY(y);
		creature.setIsAlive(1);
		creature.setId(IdUtil.getLongId());
		return creature;
	}

	// get and set
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "MapCreature{" +
				"accountId='" + accountId + '\'' +
				", x=" + x +
				", y=" + y +
				'}';
	}
}
