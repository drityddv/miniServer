package game.scene.map.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.map.IMap;
import game.base.map.base.MapCreature;
import game.common.Ii8n;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.user.player.model.Player;
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
    public void enterMap(Player player, long mapId) {
        String accountId = player.getAccountId();
        long playerId = player.getPlayerId();
        IMap map = sceneMapManager.getMapByMapId(mapId);

        if (sceneMapManager.existInMap(playerId)) {
            logger.warn("玩家[{}]当前处于地图中,请先退出地图!", accountId);
            return;
        }

        if (!map.canEnterMap(accountId)) {
            logger.warn("玩家[{}]未达到进入地图[{}]条件!", accountId, mapId);
            return;
        }

        MapCreature creature = map.getCreature(playerId);

        if (creature != null) {
            logger.warn("玩家[{}]已经存在与地图[{}],坐标[{},{}]", accountId, mapId, creature.getX(), creature.getY());
            return;
        }

        creature = MapCreature.valueOf(accountId, playerId, 0, 0);
        map.addCreature(creature);

        sceneMapManager.getPlayerMaps().put(playerId, mapId);

        PacketUtil.send(SessionManager.getSessionByAccountId(accountId), SM_Message.valueOf(Ii8n.OPERATION_SUCCESS));
    }

    // 单位不存在地图中也不会抛异常 避免客户端重复发包
    @Override
    public void leaveMap(Player player, long mapId) {
        String accountId = player.getAccountId();
        IMap map = sceneMapManager.getMapByMapId(mapId);

        if (map == null) {
            RequestException.throwException(Ii8n.MAP_NOT_EXIST);
        }

        long playerId = player.getPlayerId();

        map.deleteCreature(playerId);
        sceneMapManager.getPlayerMaps().remove(playerId);
    }

    // 这个方法暂时放空,后续增加条件再拓展
    @Override
    public void changeMap(Player player, long fromMapId, long targetMapId) {

    }

    @Override
    public void logBasicMapInfo(long mapId) {
        IMap map = sceneMapManager.getMapByMapId(mapId);
        map.printMap();
    }

    @Override
    public void transfer(Player player, long mapId) {
        long playerId = player.getPlayerId();
        IMap map = sceneMapManager.getMapByMapId(mapId);

        map.transfer(playerId);
        PacketUtil.send(SessionManager.getSessionByAccountId(player.getAccountId()),
            SM_Message.valueOf(Ii8n.OPERATION_SUCCESS));

    }

    @Override
    public void move(Player player, long mapId, int targetX, int targetY) {
        IMap map = sceneMapManager.getMapByMapId(mapId);

        map.move(player.getPlayerId(), targetX, targetY);

        PacketUtil.send(SessionManager.getSessionByAccountId(player.getAccountId()),
            SM_Message.valueOf(Ii8n.OPERATION_SUCCESS));
    }

    @Override
    public IMap getMapResource(long mapId) {
        return sceneMapManager.getMapByMapId(mapId);
    }

    @Override
    public void modifyPlayerMapStatus(long playerId, long mapId) {
        Map<Long, Long> playerMaps = sceneMapManager.getPlayerMaps();
        playerMaps.put(playerId, mapId);
    }

    @Override
    public void logOut(Player player) {
        Long mapId = sceneMapManager.getPlayerInMap(player.getPlayerId());

        if (mapId == null) {
            return;
        }

        sceneMapManager.getPlayerMaps().remove(player.getPlayerId());

        IMap map = sceneMapManager.getMapByMapId(mapId);
        map.deleteCreature(player.getPlayerId());
    }

}
