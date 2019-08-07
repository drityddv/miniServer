package game.publicsystem.rank.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.publicsystem.rank.packet.CM_RankInfo;
import game.publicsystem.rank.service.IRankService;
import game.role.player.model.Player;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/8/5 5:57 PM
 */

@Component
public class RankFacade {

    private static final Logger logger = LoggerFactory.getLogger(RankFacade.class);

    @Autowired
    private IRankService rankService;

    /**
     * 创建公会
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void getServerRankInfo(Player player, CM_RankInfo request) {
        try {
            rankService.getServerRankInfo(player);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
