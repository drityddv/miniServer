package game.user.player.model;

import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.object.AbstractCreature;
import game.user.pack.model.Pack;
import spring.SpringContext;
import utils.IdUtil;

/**
 * 做业务的对象
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:51
 */

public class Player extends AbstractCreature<Player> {

    private String accountId;

    private long playerId;

    private int level;

    private Player() {}

    public static Player valueOf(String accountId) {
        Player player = new Player();
        player.accountId = accountId;
        player.playerId = IdUtil.getLongId();
        player.level = 1;
        player.setAttributeContainer(new PlayerAttributeContainer(player));
        return player;
    }

    @Override
    public PlayerAttributeContainer getAttributeContainer() {
        return (PlayerAttributeContainer)super.getAttributeContainer();
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

    @Override
    public String toString() {
        return "Player{" + "accountId='" + accountId + '\'' + ", playerId=" + playerId + ", level=" + level + '}';
    }

}
