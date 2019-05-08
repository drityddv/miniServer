package game.scene.map.facade;

import game.common.Ii8n;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.scene.map.packet.*;
import middleware.anno.HandlerAnno;
import net.model.USession;
import net.utils.PacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spring.SpringContext;
import utils.SimpleUtil;

/**
 * 场景地图处理
 *
 * @author : ddv
 * @since : 2019/5/7 下午12:11
 */
@Component
public class SceneMapFacade {

	private static final Logger logger = LoggerFactory.getLogger(SceneMapFacade.class);

	/**
	 * 玩家进入地图
	 *
	 * @param session
	 * @param request
	 */
	@HandlerAnno
	public void enterMap(USession session, CM_EnterMap request) {
		try {
			String accountId = SimpleUtil.getAccountIdFromSession(session);
			SpringContext.getSceneMapService().enterMap(accountId, request.getMapId());
		} catch (RequestException e) {
			PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
		} catch (Exception e) {
			PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
			logger.error("玩家进入地图出错:{}", e.getMessage());
		}
	}

	/**
	 * 玩家离开地图
	 *
	 * @param session
	 * @param request
	 */
	@HandlerAnno
	public void leaveMap(USession session, CM_LeaveMap request) {
		try {
			String accountId = SimpleUtil.getAccountIdFromSession(session);
			SpringContext.getSceneMapService().leaveMap(accountId, request.getMapId());
		} catch (RequestException e) {
			PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
		} catch (Exception e) {
			PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
			logger.error("玩家离开地图出错:{}", e.getMessage());
		}
	}

	/**
	 * 玩家地图内移动
	 *
	 * @param session
	 * @param request
	 */
	@HandlerAnno
	public void move(USession session, CM_MoveMap request) {
		try {
			String accountId = SimpleUtil.getAccountIdFromSession(session);
			SpringContext.getSceneMapService().move(accountId, request.getMapId(), request.getTargetX(), request.getTargetY());
		} catch (RequestException e) {
			PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
		} catch (Exception e) {
			PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
			logger.error("玩家地图移动出错:{}", e.getMessage());
		}
	}

	/**
	 * 地图传送
	 *
	 * @param session
	 * @param request
	 */
	@HandlerAnno
	public void transfer(USession session, CM_TransferMap request) {
		try {
			String accountId = SimpleUtil.getAccountIdFromSession(session);
			SpringContext.getSceneMapService().transfer(accountId, request.getMapId());
		} catch (RequestException e) {
			PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
		} catch (Exception e) {
			PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
			logger.error("玩家地图触发传送出错:{}", e.getMessage());
		}
	}

	/**
	 * 切换地图
	 *
	 * @param session
	 * @param request
	 */
	@HandlerAnno
	public void changeMap(USession session, CM_ChangeMap request) {
		try {
			String accountId = SimpleUtil.getAccountIdFromSession(session);
			SpringContext.getSceneMapService().changeMap(accountId, request.getFromMapId(), request.getTargetMapId());
		} catch (RequestException e) {
			PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
		} catch (Exception e) {
			PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
			logger.error("玩家切换地图出错:{}", e.getMessage());
		}
	}
}
