package game.map.visible;

import game.base.fight.model.pvpunit.FighterAccount;
import game.role.player.model.Player;

/**
 * 默认的玩家地图可视对象
 *
 * @author : ddv
 * @since : 2019/7/11 3:46 PM
 */

public class PlayerVisibleMapInfo extends AbstractVisibleMapInfo {

    private String accountId;

    private long playerId;

    private Player player;

    public static PlayerVisibleMapInfo valueOf(Player player) {
        PlayerVisibleMapInfo accountInfo = new PlayerVisibleMapInfo();
        accountInfo.accountId = player.getAccountId();
        accountInfo.playerId = player.getPlayerId();
        accountInfo.player = player;
        accountInfo.fighterAccount = FighterAccount.valueOf(player);
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

    public Player getPlayer() {
        return player;
    }
}
