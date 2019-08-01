package game.world.instance.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.constant.MapGroupType;
import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.map.visible.impl.NpcObject;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.instance.model.InstanceMapInfo;
import game.world.instance.model.InstanceMapScene;
import game.world.utils.MapUtil;

/**
 * 副本
 *
 * @author : ddv
 * @since : 2019/7/30 5:47 PM
 */

@Component
public class InstanceService implements IInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(InstanceService.class);

    @Autowired
    private WorldManager worldManager;

    @Autowired
    private InstanceManager instanceManager;

    @Override
    public void flushMonsters(int mapId, long sceneId) {

    }

    @Override
    public void init() {
        List<MiniMapResource> mapResources = worldManager.getMapResourcesByGroup(MapGroupType.INSTANCE.getGroupId());
        mapResources.forEach(this::initInstanceMapInfo);
        logger.info("公共副本地图加载完成,数量[{}]", instanceManager.getMapInfoMap().size());
    }

    @Override
    public void enterMap(Player player, int mapId, long sceneId) {
        InstanceMapInfo mapInfo = instanceManager.getMapInfo(mapId);
        InstanceMapScene mapScene = mapInfo.getMapScene(sceneId);

        if (mapScene == null) {
            mapScene = mapInfo.loadSingleInstance(sceneId);
        }

        PlayerMapObject playerMapObject = PlayerMapObject.valueOf(player, mapId, sceneId);
        playerMapObject.init(mapInfo.getMiniMapResource().getBornX(), mapInfo.getMiniMapResource().getBornY());
        mapScene.enter(player.getPlayerId(), playerMapObject);
    }

    @Override
    public void leaveMap(Player player) {
        InstanceMapScene mapScene = getMapScene(player.getCurrentMapId(), player.getCurrentSceneId());
        mapScene.leave(player.getPlayerId());
    }

    @Override
    public void doMove(Player player, Grid targetGrid) {
        getMapScene(player.getCurrentMapId(), player.getCurrentSceneId()).move(player.getPlayerId(), targetGrid);
    }

    @Override
    public InstanceMapScene getCurrentScene(Player player) {
        return getMapScene(player.getCurrentMapId(), player.getCurrentSceneId());
    }

    @Override
    public void doLogMap(Player player, int mapId, long sceneId) {
        InstanceMapScene mapScene = getMapScene(mapId, sceneId);
        List<PlayerMapObject> visibleObjects = mapScene.getVisibleObjects();
        Collection<NpcObject> npcList = mapScene.getNpcMap().values();
        Collection<MonsterMapObject> monsterList = mapScene.getMonsterMap().values();
        MapUtil.log(player, mapScene, visibleObjects, npcList, monsterList);
    }

    @Override
    public InstanceMapScene getMapScene(int mapId, long sceneId) {
        return instanceManager.getMapInfo(mapId).getMapScene();
    }

    @Override
    public void heartBeat(int mapId) {
        instanceManager.getMapInfoMap().values().forEach(instanceMapInfo -> {
            instanceMapInfo.getMapScene().tryDestroy();
        });
    }

    @Override
    public void destroy(int mapId, long sceneId) {
        logger.info("销毁副本[{}]", mapId);
        instanceManager.destroy(mapId);
    }

    @Override
    public void close(int mapId, long sceneId) {
        getMapScene(mapId, sceneId).close();
    }

    private InstanceMapInfo loadMapInfo(int mapId, long sceneId) {
        MiniMapResource mapResource = worldManager.getMapResource(mapId);
        InstanceMapInfo mapInfo = InstanceMapInfo.valueOf(mapResource);
        instanceManager.addMapInfo(mapInfo);
        return mapInfo;
    }

    private InstanceMapInfo initInstanceMapInfo(MiniMapResource mapResource) {
        InstanceMapInfo mapInfo = InstanceMapInfo.valueOf(mapResource);
        instanceManager.addMapInfo(mapInfo);
        return mapInfo;
    }

    private boolean isInstanceExist(int mapId, long sceneId) {
        InstanceMapInfo mapInfo = getMapInfo(mapId);
        return mapInfo.containsScene(sceneId);
    }

    private InstanceMapInfo getMapInfo(int mapId) {
        return instanceManager.getMapInfo(mapId);
    }
}
