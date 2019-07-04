package game.world.neutralMap.model;

import game.miniMap.visible.AbstractVisibleMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/3 下午3:58
 */

public class NeutralMapAccountInfo extends AbstractVisibleMapInfo {

    private String accountId;

    private long playerId;

    public static NeutralMapAccountInfo valueOf(String accountId, long playerId) {
        NeutralMapAccountInfo accountInfo = new NeutralMapAccountInfo();
        accountInfo.accountId = accountId;
        accountInfo.playerId = playerId;
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
}
