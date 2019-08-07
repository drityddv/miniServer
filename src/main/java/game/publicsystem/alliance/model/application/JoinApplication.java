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

    // 同一行会申请只有一份 多个行会通过player上锁 根据返回值处理
    @Override
    public synchronized boolean handler(boolean agreed) {
        if (expired) {
            return false;
        }
        Alliance alliance = SpringContext.getAllianceService().getAlliance(allianceId);
        if (agreed) {
            Player player = SpringContext.getPlayerService().getPlayerByAccountId(accountId);
            if (player.changeAllianceId(allianceId, false)) {
                alliance.addMember(player.getPlayerId());
            }
            SpringContext.getAllianceService().sendAllianceInfo(player);
        }
        alliance.getApplicationMap().get(getOperationType()).remove(playerId);
        expired = true;
        return true;
    }

}
