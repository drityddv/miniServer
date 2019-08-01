package game.world.instance.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.base.AbstractMovableScene;
import game.map.constant.MapGroupType;
import game.map.handler.AbstractMapHandler;
import game.map.handler.IMovableMapHandler;
import game.map.handler.ISceneMapHandler;
import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.role.player.model.Player;
import game.world.instance.model.InstanceMapScene;
import game.world.instance.service.InstanceService;

/**
 * 副本地图处理器
 *
 * @author : ddv
 * @since : 2019/7/30 5:13 PM
 */
@Component
public class InstanceMapHandler extends AbstractMapHandler
    implements IMovableMapHandler, ISceneMapHandler<InstanceMapScene> {

    @Autowired
    private InstanceService instanceService;

    @Override
    public void doLogMap(Player player, int mapId, long sceneId) {
        instanceService.doLogMap(player, mapId, sceneId);
    }

    @Override
    public InstanceMapScene getCurrentScene(Player player) {
        return instanceService.getCurrentScene(player);
    }

    @Override
    public MapGroupType getGroupType() {
        return MapGroupType.INSTANCE;
    }

    @Override
    public void leaveMap(Player player) {
        instanceService.leaveMap(player);
    }

    @Override
    public void realEnterMap(Player player, int mapId, long sceneId) {
        instanceService.enterMap(player, mapId, sceneId);
    }

    @Override
    public AbstractMovableScene getMapScene(int mapId, long sceneId) {
        return instanceService.getMapScene(mapId, sceneId);
    }

    @Override
    public void move(Player player, Grid targetGrid) {
        instanceService.doMove(player, targetGrid);
    }

    @Override
    public void heartBeat(int mapId) {
        instanceService.heartBeat(mapId);
    }

    @Override
    public Map<Long, PlayerMapObject> getPlayerObjects(int mapId, long sceneId) {
        return instanceService.getMapScene(mapId, sceneId).getPlayerMap();
    }

    @Override
    public Map<Long, MonsterMapObject> getMonsterObjects(int mapId, long sceneId) {
        return instanceService.getMapScene(mapId, sceneId).getMonsterMap();
    }

    @Override
    public void showAround(Player player) {
        move(player, getCurrentScene(player).getMapObject(player.getPlayerId()).getCurrentGrid());
    }

    @Override
    public void closeInstance(int mapId, long sceneId) {
        instanceService.close(mapId, sceneId);
    }
}
