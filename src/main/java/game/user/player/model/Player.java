package game.user.player.model;

import game.base.object.AbstractCreature;

/**
 * 做业务的对象
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:51
 */

public class Player extends AbstractCreature {

	private String accountId;

	// 等级
	private int level;

	// 玩家战斗力
	private long battleScore;

	private Player() {
	}

	public static Player valueOf(String accountId) {
		Player player = new Player();
		player.setAccountId(accountId);
		player.setBattleScore(0);
		player.setLevel(1);
		return player;
	}

	// get and set

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getBattleScore() {
		return battleScore;
	}

	public void setBattleScore(long battleScore) {
		this.battleScore = battleScore;
	}

}
