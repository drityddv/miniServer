package game.scene.map.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.map.IMap;
import game.base.map.base.MapCreature;
import game.common.Ii8n;
import game.common.packet.SM_Message;
import middleware.manager.SessionManager;
import net.utils.PacketUtil;

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
    public void enterMap(String accountId, long mapId) {
        IMap map = sceneMapManager.getMapByMapId(mapId);

        if (!map.canEnterMap(accountId)) {
            logger.warn("玩家[{}]未达到进入地图[{}]条件!", accountId, mapId);
            return;
        }

        long playerId = getPlayerId(accountId);
        MapCreature creature = map.getCreature(playerId);

        if (creature != null) {
            logger.warn("玩家[{}]已经存在与地图[{}],坐标[{},{}]", accountId, mapId, creature.getX(), creature.getY());
            return;
        }

        creature = MapCreature.valueOf(accountId, getPlayerId(accountId), 0, 0);
        map.addCreature(creature);

        PacketUtil.send(SessionManager.getSessionByAccountId(accountId), SM_Message.valueOf(Ii8n.OPERATION_SUCCESS));
    }

    // 单位不存在地图中也不会抛异常 避免客户端重复发包
    @Override
    public void leaveMap(String accountId, long mapId) {
        IMap map = sceneMapManager.getMapByMapId(mapId);
        long playerId = getPlayerId(accountId);

        map.deleteCreature(playerId);
    }

    // 这个方法暂时放空,后续增加条件再拓展
    @Override
    public void changeMap(String accountId, long fromMapId, long targetMapId) {

    }

    @Override
    public void logBasicMapInfo(long mapId) {
        IMap map = sceneMapManager.getMapByMapId(mapId);
        map.printMap();
    }

    @Override
    public void transfer(String accountId, long mapId) {
        IMap map = sceneMapManager.getMapByMapId(mapId);

        map.transfer(getPlayerId(accountId));
        PacketUtil.send(SessionManager.getSessionByAccountId(accountId), SM_Message.valueOf(Ii8n.OPERATION_SUCCESS));

    }

    @Override
    public void move(String accountId, long mapId, int targetX, int targetY) {
        IMap map = sceneMapManager.getMapByMapId(mapId);

        map.move(getPlayerId(accountId), targetX, targetY);

        PacketUtil.send(SessionManager.getSessionByAccountId(accountId), SM_Message.valueOf(Ii8n.OPERATION_SUCCESS));
    }

    @Override
    public IMap getMapResource(long mapId) {
        return sceneMapManager.getMapByMapId(mapId);
    }

    // 临时的player转化工具
    // 原则上应该使用player做业务,完成accountId到playerId的转化,这里先写死,后续添加player模块后修改
    private long getPlayerId(String accountId) {
        return accountId.hashCode();
    }
}
