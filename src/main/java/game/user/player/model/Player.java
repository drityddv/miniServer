package game.user.player.model;

import game.base.game.attribute.AttributeContainer;
import game.user.pack.model.Pack;
import spring.SpringContext;
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

    // 战斗单元
    private transient AttributeContainer battleUnit;

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

    public Pack getPack() {
        return SpringContext.getPackService().getPlayerPack(this);
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

    public AttributeContainer getBattleUnit() {
        return battleUnit;
    }

    public void setBattleUnit(AttributeContainer battleUnit) {
        this.battleUnit = battleUnit;
    }

    @Override
    public String toString() {
        return "Player{" + "accountId='" + accountId + '\'' + ", playerId=" + playerId + ", level=" + level + '}';
    }

}
