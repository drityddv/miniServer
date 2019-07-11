package game.map.visible.impl;

import game.base.fight.model.pvpunit.FighterAccount;
import game.map.visible.AbstractVisibleMapInfo;
import game.map.visible.VisibleStatus;
import game.role.player.model.Player;
import spring.SpringContext;

/**
 * 玩家地图单位
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:57
 */

public class AccountVisibleMapInfo extends AbstractVisibleMapInfo {

    private String accountId;
    /**
     * 战斗属性
     */
    private FighterAccount fighterAccount;
    /**
     * 状态
     */
    private VisibleStatus status;

    public static AccountVisibleMapInfo valueOf(Player player) {
        AccountVisibleMapInfo playerMapInfo = new AccountVisibleMapInfo();
        playerMapInfo.accountId = player.getAccountId();
        playerMapInfo.fighterAccount = SpringContext.getFightService().initForPlayer(player);
        playerMapInfo.status = VisibleStatus.NORMAL;
        return playerMapInfo;
    }

    @Override
    public long getId() {
        return fighterAccount.getCreatureUnit().getId();
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    public VisibleStatus getStatus() {
        return status;
    }

    public void setStatus(VisibleStatus status) {
        this.status = status;
    }
}
