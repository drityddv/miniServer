package game.world.base.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.scene.map.packet.CM_ChangeMap;
import game.user.player.model.Player;
import game.world.base.packet.CM_LogMap;
import game.world.base.service.IWorldService;
import middleware.anno.HandlerAnno;
import net.utils.PacketUtil;

/**
 * 所有的地图都从这里走
 *
 * @author : ddv
 * @since : 2019/7/2 下午4:37
 */
@Component
public class WorldFacade {

    @Autowired
    private IWorldService worldService;

    /**
     * 客户端切图
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void changeMap(Player player, CM_ChangeMap request) {
        try {
            worldService.changeMap(player, request.getMapId(), true);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @HandlerAnno
    public void logMap(Player player, CM_LogMap request) {
        try {
            worldService.logMap(player, request.getMapId());
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
