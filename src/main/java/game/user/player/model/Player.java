package game.user.player.model;

import game.base.game.attribute.AttributeContainer;
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

    private AttributeContainer attributeContainer;

    private Player() {}

    public static Player valueOf(String accountId) {
        Player player = new Player();
        player.accountId = accountId;
        player.playerId = IdUtil.getLongId();
        player.level = 1;
        player.attributeContainer = AttributeContainer.valueOf(player.playerId);
        return player;
    }

    public AttributeContainer getAttributeContainer() {
        if (attributeContainer == null) {
            attributeContainer = AttributeContainer.valueOf(playerId);
        }
        return attributeContainer;
    }

    // private AttributeContainer recopute

    public void setAttributeContainer(AttributeContainer attributeContainer) {
        this.attributeContainer = attributeContainer;
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
