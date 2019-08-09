package game.publicsystem.alliance.packet;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.role.player.model.PlayerAllianceInfo;

/**
 * 玩家行会信息vo
 *
 * @author : ddv
 * @since : 2019/8/8 11:44 PM
 */

public class SM_PlayerAllianceVo {
    private static final Logger logger = LoggerFactory.getLogger(SM_PlayerAllianceVo.class);
    private long allianceId;
    private Map<Long, Long> inviteMap;

    public static SM_PlayerAllianceVo valueOf(PlayerAllianceInfo playerAllianceInfo) {
        SM_PlayerAllianceVo sm = new SM_PlayerAllianceVo();

        sm.allianceId = playerAllianceInfo.getAllianceId();
        sm.inviteMap = new HashMap<>(playerAllianceInfo.getInviteMap());

        return sm;
    }

    @Action
    private void action() {
        logger.info("行会id[{}]", allianceId);
        inviteMap.forEach((inviteId, allianceId) -> {
            logger.info("邀请者[{}] 目标行会[{}]", inviteId, allianceId);
        });
    }
}
