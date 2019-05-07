package game.gm.service;

import game.user.login.entity.UserEnt;
import net.model.USession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spring.SpringContext;

/**
 * gm命令后台实现
 *
 * @author : ddv
 * @since : 2019/5/7 下午3:01
 */

@Component
public class GM_Command {

	private static final Logger logger = LoggerFactory.getLogger(GM_Command.class);

	public void logUserEnt(USession session) {
		UserEnt userEnt = SpringContext.getLoginService().getUserEnt(session);
		logger.info(userEnt.toString());
	}

	public void logBasicMapInfo(USession session, long mapId) {
		SpringContext.getSceneMapService().logBasicMapInfo(mapId);
	}

}
