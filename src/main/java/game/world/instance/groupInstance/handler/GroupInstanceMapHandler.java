package game.world.instance.groupInstance.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.base.AbstractMovableScene;
import game.map.constant.MapGroupType;
import game.map.handler.IMovableMapHandler;
import game.map.handler.ISceneMapHandler;
import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.role.player.model.Player;
import game.world.instance.base.handler.AbstractInstanceMapHandler;
import game.world.instance.base.model.BaseInstanceMapScene;
import game.world.instance.groupInstance.service.GroupInstanceService;

/**
 * 多人副本地图处理器
 *
 * @author : ddv
 * @since : 2019/7/30 5:13 PM
 */
@Component
public class GroupInstanceMapHandler extends AbstractInstanceMapHandler
    implements IMovableMapHandler, ISceneMapHandler<BaseInstanceMapScene> {

    @Autowired
    private GroupInstanceService groupInstanceService;

    @Override
    public MapGroupType getGroupType() {
        return MapGroupType.GROUP_INSTANCE;
    }

    @Override
    public void doLogMap(Player player, int mapId, long sceneId) {
        groupInstanceService.doLogMap(player, mapId, sceneId);
    }

    @Override
    public BaseInstanceMapScene getCurrentScene(Player player) {
        return groupInstanceService.getCurrentScene(player);
    }

    @Override
    public void leaveMap(Player player) {
        groupInstanceService.leaveMap(player);
    }

    @Override
    public void realEnterMap(Player player, int mapId, long sceneId) {
        groupInstanceService.enterMap(player, mapId, sceneId);
    }

    @Override
    public AbstractMovableScene getMapScene(int mapId, long sceneId) {
        BaseInstanceMapScene mapScene = groupInstanceService.getMapScene(mapId, sceneId);
        return mapScene;
    }

    @Override
    public void move(Player player, Grid targetGrid) {
        groupInstanceService.doMove(player, targetGrid);
    }

    @Override
    public void heartBeat(int mapId) {
        groupInstanceService.heartBeat(mapId);
    }

    @Override
    public Map<Long, PlayerMapObject> getPlayerObjects(int mapId, long sceneId) {
        return groupInstanceService.getMapScene(mapId, sceneId).getPlayerMap();
    }

    @Override
    public Map<Long, MonsterMapObject> getMonsterObjects(int mapId, long sceneId) {
        return groupInstanceService.getMapScene(mapId, sceneId).getMonsterMap();
    }

    @Override
    public void closeInstance(int mapId, long sceneId) {
        groupInstanceService.close(mapId, sceneId);
    }
}
