package game.scene.map.facade;

import game.common.Ii8n;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.scene.map.packet.CM_EnterMap;
import middleware.anno.HandlerAnno;
import net.model.USession;
import net.utils.PacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spring.SpringContext;

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
			String accountId = (String) session.getAttributes().get("accountId");
			SpringContext.getSceneMapService().enterMap(accountId, request);
		} catch (RequestException e) {
			PacketUtil.send(session, SM_Message.valueOf(e.getErrorCode()));
		} catch (Exception e) {
			PacketUtil.send(session, SM_Message.valueOf(Ii8n.SERVER_ERROR));
			logger.error("玩家进入地图出错:{}", e.getMessage());
		}
	}
}
