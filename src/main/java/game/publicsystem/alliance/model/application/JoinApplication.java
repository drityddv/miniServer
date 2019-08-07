package game.publicsystem.alliance.model.application;

import game.publicsystem.alliance.constant.OperationType;
import game.publicsystem.alliance.model.Alliance;
import game.role.player.model.Player;
import spring.SpringContext;

/**
 * 加入行会申请
 *
 * @author : ddv
 * @since : 2019/8/3 3:42 PM
 */

public class JoinApplication extends BaseAllianceApplication {

    public JoinApplication(Player player, OperationType operationType, long allianceId) {
        super(player, operationType, allianceId);
    }

    public static JoinApplication valueOf(Player player, long allianceId) {
        JoinApplication joinApplication = new JoinApplication(player, OperationType.Join_Alliance, allianceId);
        return joinApplication;
    }

    @Override
    public boolean handler(boolean agreed) {
        boolean success = false;
        if (expired) {
            return false;
        }
        Alliance alliance = SpringContext.getAllianceService().getAlliance(allianceId);
        // 同意入会
        if (agreed) {
            Player player = SpringContext.getPlayerService().getPlayerByAccountId(accountId);
            // 尝试修改player 判断是否成功
            success = player.changeAllianceId(allianceId, false);
            if (success) {
                alliance.addMember(player.getPlayerId());
            }
        }

        /**
         * 结果没有进会删掉对应锁关于锁是否会冲突申请的问题 锁申请只会在第一次提交申请的时候加入 申请之前会检查申请表状态 锁删除只会在退会或者拒绝加入行会的时候
         */

        if (!success) {
            alliance.removeLock(playerId);
        }
        // 删除请求
        alliance.getApplicationMap().get(getOperationType()).remove(playerId);
        expired = true;
        return true;
    }

}
