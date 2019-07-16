package game.world.fight.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.role.player.model.Player;
import game.world.fight.packet.CM_UseSinglePointSkill;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/16 12:22 PM
 */
@Component
public class FightFacade {

    private static final Logger logger = LoggerFactory.getLogger(FightFacade.class);

    /**
     * 使用单体指向性技能
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void useSinglePointSkill(Player player, CM_UseSinglePointSkill request) {
        try {
            SpringContext.getFightService().useSinglePointSkill(player, request.getSkillId(), request.getTargetId());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
