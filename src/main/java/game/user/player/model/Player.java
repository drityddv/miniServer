package game.user.player.model;

import utils.IdUtil;

/**
 * 做业务的对象
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:51
 */

public class Player {

    private String accountId;

    private long playerId;

    private int level;

    private Player() {}

    public static Player valueOf(String accountId) {
        Player player = new Player();
        player.setAccountId(accountId);
        player.setPlayerId(IdUtil.getLongId());
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

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "Player{" + "accountId='" + accountId + '\'' + ", playerId=" + playerId + ", level=" + level + '}';
    }
}
