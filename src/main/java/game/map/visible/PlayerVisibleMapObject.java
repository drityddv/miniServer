package game.map.visible;

import game.base.fight.model.pvpunit.FighterAccount;
import game.role.player.model.Player;

/**
 * 默认的玩家地图可视对象
 *
 * @author : ddv
 * @since : 2019/7/11 3:46 PM
 */

public class PlayerVisibleMapObject extends BaseAttackAbleMapObject {

    private String accountId;

    private long playerId;

    private Player player;

    public static PlayerVisibleMapObject valueOf(Player player, int mapId) {
        PlayerVisibleMapObject accountInfo = new PlayerVisibleMapObject();
        accountInfo.accountId = player.getAccountId();
        accountInfo.playerId = player.getPlayerId();
        accountInfo.player = player;
        accountInfo.fighterAccount = FighterAccount.valueOf(player, mapId);
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
