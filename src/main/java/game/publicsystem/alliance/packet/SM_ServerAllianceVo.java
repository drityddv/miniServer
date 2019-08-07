package game.publicsystem.alliance.packet;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.publicsystem.alliance.model.Alliance;
import game.publicsystem.alliance.model.ServerAllianceInfo;

/**
 * @author : ddv
 * @since : 2019/8/5 3:00 PM
 */

public class SM_ServerAllianceVo {
    private static final Logger logger = LoggerFactory.getLogger(SM_ServerAllianceVo.class);

    private long serverId;

    private Map<Long, Alliance> allianceMap;

    public static SM_ServerAllianceVo valueOf(ServerAllianceInfo allianceInfo) {
        SM_ServerAllianceVo vo = new SM_ServerAllianceVo();
        vo.serverId = allianceInfo.getServerId();
        vo.allianceMap = allianceInfo.getAllianceMap();
        return vo;
    }

    @Action
    private void action() {
        logger.info("服务器[{}]", serverId);
        allianceMap.values().forEach(alliance -> {
            Set<Long> memberSet = alliance.getMemberSet();
            logger.info("公会[{} {}] 会长[{}] 成员数[{}]", alliance.getAllianceId(), alliance.getAllianceName(),
                alliance.getChairmanId(), memberSet.size());

            alliance.getMemberLocks().keySet().forEach(playerId -> {
                logger.info("成员锁[{}]", playerId);
            });

            memberSet.forEach(playerId -> {
                logger.info("成员[{}]", playerId);
            });

            alliance.getApplicationMap().forEach((operationType, applicationMap) -> {
                logger.info("申请操作[{}]", operationType.name());
                applicationMap.values().forEach(allianceApplication -> {
                    logger.info("申请id[{}]", allianceApplication.getPlayerId());
                });
            });
        });
    }
}
