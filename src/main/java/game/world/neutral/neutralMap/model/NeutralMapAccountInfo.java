package game.world.neutral.neutralMap.model;

import game.map.visible.AbstractVisibleMapInfo;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/3 下午3:58
 */

public class NeutralMapAccountInfo extends AbstractVisibleMapInfo {

    private String accountId;

    private long playerId;

    private Player player;

    public static NeutralMapAccountInfo valueOf(Player player, String accountId, long playerId) {
        NeutralMapAccountInfo accountInfo = new NeutralMapAccountInfo();
        accountInfo.accountId = accountId;
        accountInfo.playerId = playerId;
        accountInfo.player = player;
        return accountInfo;
    }

    @Override
    public long getId() {
        return playerId;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
