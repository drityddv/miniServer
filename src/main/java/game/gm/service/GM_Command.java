package game.gm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.scene.map.service.SceneMapManager;
import game.user.login.entity.UserEnt;
import net.model.USession;
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

    @Autowired
    private SceneMapManager sceneMapManager;

    public void logUserEnt(USession session) {
        UserEnt userEnt = SpringContext.getLoginService().getUserEnt(session);
        logger.info(userEnt.toString());
    }

    public void logMap(USession session, long mapId) {
        logger.info("服务器当前地图[{}]张", sceneMapManager.getSceneMaps().size());
        SpringContext.getSceneMapService().logBasicMapInfo(mapId);
    }

}
