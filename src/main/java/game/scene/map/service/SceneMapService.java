package game.scene.map.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.map.IMap;
import game.base.map.base.MapCreature;
import game.scene.map.packet.CM_ChangeMap;
import game.scene.map.packet.CM_EnterMap;
import game.scene.map.packet.CM_LeaveMap;

/**
 * @author : ddv
 * @since : 2019/5/7 上午10:34
 */
@Component
public class SceneMapService implements ISceneMapService {

	private static final Logger logger = LoggerFactory.getLogger(SceneMapService.class);

	@Autowired
	private SceneMapManager sceneMapManager;

	@Override
	public void enterMap(String accountId, CM_EnterMap request) {
		long mapId = request.getMapId();
		IMap map = sceneMapManager.getMapByMapId(mapId);

		if (!map.canEnterMap(accountId)) {
			logger.warn("玩家[{}]未达到进入地图[{}]条件!", accountId, mapId);
			return;
		}

		// 原则上应该使用player做业务,完成accountId到playerId的转化,这里先写死1L,后续添加player模块后修改
		long playerId = getPlayerId(accountId);

		MapCreature creature = map.getCreature(playerId);

		if (creature != null) {
			logger.warn("玩家[{}]已经存在与地图[{}],坐标[{},{}]", accountId, mapId, creature.getX(), creature.getY());
			return;
		}

		creature = MapCreature.valueOf(accountId, 0, 0);
		map.addCreature(creature);

		logger.info("");
	}

	@Override
	public void leaveMap(String accountId, CM_LeaveMap request) {

	}

	@Override
	public void changeMap(String accountId, CM_ChangeMap request) {

	}

	@Override
	public void logBasicMapInfo(long mapId) {
		IMap map = sceneMapManager.getMapByMapId(mapId);
		if (map == null) {
			logger.warn("指定[{}]的地图不存在!", mapId);
			return;
		}

		map.printMap();
	}

	// 临时的player转化工具
	private long getPlayerId(String accountId) {
		return 1L;
	}
}
