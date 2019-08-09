package game.world.instance.groupInstance.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.constant.MapGroupType;
import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.instance.base.model.BaseInstanceMapScene;
import game.world.instance.groupInstance.model.GroupInstanceMapInfo;

/**
 * 副本
 *
 * @author : ddv
 * @since : 2019/7/30 5:47 PM
 */

@Component
public class GroupInstanceService implements IGroupInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(GroupInstanceService.class);

    @Autowired
    private WorldManager worldManager;

    @Autowired
    private GroupInstanceManager groupInstanceManager;

    @Override
    public void init() {
        List<MiniMapResource> mapResources =
            worldManager.getMapResourcesByGroup(MapGroupType.GROUP_INSTANCE.getGroupId());
        mapResources.forEach(this::initGroupInstanceMapInfo);
        logger.info("多人副本地图加载完成,数量[{}]", groupInstanceManager.getMapInfoMap().size());
    }

    @Override
    public void enterMap(Player player, int mapId, long sceneId) {
        GroupInstanceMapInfo mapInfo = groupInstanceManager.getMapInfo(mapId);
        BaseInstanceMapScene mapScene = mapInfo.getMapScene(sceneId);

        PlayerMapObject playerMapObject = PlayerMapObject.valueOf(player, mapId, sceneId);
        playerMapObject.init(mapInfo.getMiniMapResource().getBornX(), mapInfo.getMiniMapResource().getBornY());
        mapScene.enter(player.getPlayerId(), playerMapObject);
    }

    @Override
    public void leaveMap(Player player) {
        BaseInstanceMapScene mapScene = getMapScene(player.getCurrentMapId(), player.getCurrentSceneId());
        mapScene.leave(player.getPlayerId());
    }

    @Override
    public void doMove(Player player, Grid targetGrid) {
        getMapScene(player.getCurrentMapId(), player.getCurrentSceneId()).move(player.getPlayerId(), targetGrid);
    }

    @Override
    public BaseInstanceMapScene getCurrentScene(Player player) {
        return getMapScene(player.getCurrentMapId(), player.getCurrentSceneId());
    }

    @Override
    public void doLogMap(Player player, int mapId, long sceneId) {

    }

    @Override
    public BaseInstanceMapScene getMapScene(int mapId, long sceneId) {
        return groupInstanceManager.getMapInfo(mapId).getMapScene();
    }

    @Override
    public void heartBeat(int mapId) {}

    @Override
    public void destroy(int mapId, long sceneId) {
        logger.info("销毁副本[{}]", mapId);
        groupInstanceManager.removeMapInfo(mapId);
    }

    @Override
    public void close(int mapId, long sceneId) {
        getMapScene(mapId, sceneId).close();
    }

    @Override
    public long getSceneId(int mapId, long sceneId) {
        return sceneId;
    }

    private GroupInstanceMapInfo initGroupInstanceMapInfo(MiniMapResource mapResource) {
        GroupInstanceMapInfo mapInfo = GroupInstanceMapInfo.valueOf(mapResource);
        groupInstanceManager.addMapInfo(mapInfo);
        return mapInfo;
    }
}
