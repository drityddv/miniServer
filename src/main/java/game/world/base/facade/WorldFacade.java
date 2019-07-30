package game.world.base.facade;

import game.world.base.packet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.dispatch.anno.HandlerAnno;
import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.base.constant.Map_Constant;
import game.world.base.service.IWorldService;
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
    public void gatewayChangeMap(Player player, CM_ChangeMap request) {
        try {
            worldService.gatewayChangeMap(player, request.getMapId(), true);
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @HandlerAnno
    public void gatewayLeaveMap(Player player, CM_LeaveMap request) {
        try {
            worldService.gatewayLeaveMap(player, Map_Constant.EMPTY_MAP, true);
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

    /**
     * 地图内移动
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void move(Player player, CM_MoveMap request) {
        try {
            worldService.move(player, Grid.valueOf(request.getTargetX(), request.getTargetY()));
        } catch (RequestException e) {
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


	@HandlerAnno
	public void showAround(Player player, CM_ShowAround request) {
		try {
			worldService.showAround(player, request);
		} catch (RequestException e) {
			PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
