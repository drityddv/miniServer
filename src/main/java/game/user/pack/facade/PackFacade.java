package game.user.pack.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.role.player.model.Player;
import game.user.pack.packet.CM_PackInfo;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/6/10 下午6:43
 */
@Component
public class PackFacade {

    private static final Logger logger = LoggerFactory.getLogger(PackFacade.class);

    /**
     * 玩家获取背包详情
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void getPlayerPack(Player player, CM_PackInfo request) {
        try {
            SpringContext.getPackService().getPlayerPack(player, true);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(player, SM_Message.valueOf(I18N.SERVER_ERROR));
            e.printStackTrace();
        }
    }
}
