package game.publicsystem.alliance.model.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.publicsystem.alliance.constant.OperationType;
import game.role.player.model.Player;

/**
 * 玩家申请基类
 *
 * @author : ddv
 * @since : 2019/8/4 3:43 PM
 */

public abstract class BaseAllianceApplication {
    protected static final Logger logger = LoggerFactory.getLogger(BaseAllianceApplication.class);
    protected volatile Long playerId;
    protected String accountId;
    protected long allianceId;
    protected volatile boolean expired = false;

    protected OperationType operationType;

    public BaseAllianceApplication(Player player, OperationType operationType, long allianceId) {
        this.playerId = player.getPlayerId();
        this.accountId = player.getAccountId();
        this.allianceId = allianceId;
        this.operationType = operationType;
    }

    /**
     * 处理申请 考虑到入库等情况 如果子类需要loadPlayer参数 自己去实现
     *
     * @param agreed
     * @return 是否需要保存行会数据
     */
    public abstract boolean handler(boolean agreed);

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getAccountId() {
        return accountId;
    }
}
