package game.publicsystem.alliance.model;

import game.publicsystem.alliance.constant.OperationType;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/8/9 6:12 PM
 */

public class AllianceParam {
    private long allianceId;
    // 操作执行者
    private Player player;
    // 操作的对象
    private Player targetPlayer;
    // 申请表类型
    private OperationType operationType;
    // 申请表id
    private long applicationId;

    public static AllianceParam valueOf(Player player, long allianceId) {
        AllianceParam param = new AllianceParam();
        param.allianceId = allianceId;
        param.player = player;
        return param;
    }

    public static AllianceParam valueOf(Player player, long allianceId, Player targetPlayer) {
        AllianceParam param = new AllianceParam();
        param.allianceId = allianceId;
        param.player = player;
        param.targetPlayer = targetPlayer;
        return param;
    }

    public long getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(long allianceId) {
        this.allianceId = allianceId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }
}
