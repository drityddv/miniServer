package game.role.equip.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.common.I18N;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.role.equip.packet.*;
import game.role.equip.service.IEquipService;
import game.role.player.model.Player;
import game.user.login.event.PlayerLoginBeforeEvent;
import middleware.anno.EventReceiver;
import middleware.anno.HandlerAnno;
import net.utils.PacketUtil;

/**
 * 玩家装备栏
 *
 * @author : ddv
 * @since : 2019/7/1 下午4:10
 */
@Component
public class EquipFacade {

    private static final Logger logger = LoggerFactory.getLogger(EquipFacade.class);

    @Autowired
    private IEquipService equipService;

    /**
     * 穿戴装备
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void equip(Player player, CM_DressEquip request) {
        try {
            equipService.equip(player, request.getEquipmentConfigId(), request.getPosition());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(player, SM_Message.valueOf(I18N.SERVER_ERROR));
            e.printStackTrace();
        }
    }

    /**
     * 脱卸装备
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void unDress(Player player, CM_UndressEquip request) {
        try {
            equipService.unDress(player, request.getPosition());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(player, SM_Message.valueOf(I18N.SERVER_ERROR));
            e.printStackTrace();
        }
    }

    /**
     * 客户端请求装备栏指定槽位的数据
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void requestEquipment(Player player, CM_Equipment request) {
        try {
            equipService.requestEquipment(player, request.getPosition());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(player, SM_Message.valueOf(I18N.SERVER_ERROR));
            e.printStackTrace();
        }
    }

    /**
     * 强化装备孔
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void enhance(Player player, CM_EnhanceEquipSquare request) {
        try {
            equipService.enhance(player, request.getPosition());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(player, SM_Message.valueOf(I18N.SERVER_ERROR));
            e.printStackTrace();
        }
    }

    /**
     * 查看装备栏数据
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void requestEquipStorage(Player player, CM_EquipStorage request) {
        try {
            equipService.requestEquipStorage(player);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            PacketUtil.send(player, SM_Message.valueOf(I18N.SERVER_ERROR));
            e.printStackTrace();
        }
    }

    /**
     * 登陆加载装备栏
     *
     * @param event
     */
    @EventReceiver
    public void loadEquipStorage(PlayerLoginBeforeEvent event) {
        Player player = event.getPlayer();
        try {
            equipService.loadEquipStorage(player);
        } catch (Exception e) {
            logger.warn("加载玩家装备栏数据出错");
            e.printStackTrace();
        }

    }
}
