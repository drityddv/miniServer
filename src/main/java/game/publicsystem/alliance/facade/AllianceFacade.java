package game.publicsystem.alliance.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.publicsystem.alliance.packet.*;
import game.publicsystem.alliance.service.IAllianceService;
import game.role.equip.facade.EquipFacade;
import game.role.player.model.Player;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/8/4 7:26 PM
 */
@Component
public class AllianceFacade {

    private static final Logger logger = LoggerFactory.getLogger(EquipFacade.class);

    @Autowired
    private IAllianceService allianceService;

    /**
     * 创建公会
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void createAlliance(Player player, CM_CreateAlliance request) {
        try {
            allianceService.createAlliance(player, request.getAllianceName());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请加入公会
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void joinApplication(Player player, CM_JoinApplication request) {
        try {
            allianceService.joinApplication(player, request.getAllianceId());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请离开公会
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void leaveApplication(Player player, CM_LeaveApplication request) {
        try {
            allianceService.leaveApplication(player, request.getForce() == 1);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理公会相关申请
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void handlerApplication(Player player, CM_HandlerApplication request) {
        try {
            allianceService.handlerApplication(player, request.getOperationTypeId(), request.getApplicationId(),
                request.getAgreed() == 1);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理公会相关申请
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void kickMember(Player player, CM_KickMember request) {
        try {
            allianceService.kickMember(player, request.getPlayerId());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看服务器所有公会信息
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void getAllianceInfo(Player player, CM_ServerAlliances request) {
        try {
            allianceService.getAllianceInfo(player);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
