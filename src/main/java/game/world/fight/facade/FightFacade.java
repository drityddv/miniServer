package game.world.fight.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.role.player.model.Player;
import game.world.fight.packet.*;
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

    /**
     * 使用群体指向性技能
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void useGroupPointSkill(Player player, CM_UseGroupPointSkill request) {
        try {
            SpringContext.getFightService().useGroupPointSkill(player, request.getSkillId(), request.getTargetIdList());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用aoe技能
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void useAoeSkill(Player player, CM_UserAoeSkill request) {
        try {
            SpringContext.getFightService().useAoeSkill(player, request.getSkillId(), request.getCenterX(),
                request.getCenterY(), request.getCenterType());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用自身性技能
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void useSelfSkill(Player player, CM_UseSelfSkill request) {
        try {
            SpringContext.getFightService().useSelfSkill(player, request.getSkillId());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看单位战斗单元信息
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void logUnitBattleInfo(Player player, CM_logUnitBattleInfo request) {
        try {
            SpringContext.getFightService().logUnitBattleInfo(player, request.getUnitId());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
