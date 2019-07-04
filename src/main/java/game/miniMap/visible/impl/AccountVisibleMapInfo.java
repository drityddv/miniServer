package game.miniMap.visible.impl;

import game.miniMap.visible.AbstractVisibleMapInfo;
import game.miniMap.visible.VisibleStatus;

/**
 * 账号可视对象
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:57
 */

public class AccountVisibleMapInfo extends AbstractVisibleMapInfo {

    private String accountId;
    private long playerId;

    private VisibleStatus status;

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

    public VisibleStatus getStatus() {
        return status;
    }

    public void setStatus(VisibleStatus status) {
        this.status = status;
    }
}
