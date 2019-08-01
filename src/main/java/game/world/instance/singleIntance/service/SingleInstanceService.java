package game.world.instance.singleIntance.service;

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
import game.world.instance.base.model.BaseInstanceMapScene;
import game.world.instance.singleIntance.model.SingleInstanceMapInfo;
import game.world.utils.MapUtil;

/**
 * 副本
 *
 * @author : ddv
 * @since : 2019/7/30 5:47 PM
 */

@Component
public class SingleInstanceService implements ISingleInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(SingleInstanceService.class);

    @Autowired
    private WorldManager worldManager;

    @Autowired
    private SingleInstanceManager singleInstanceManager;

    @Override
    public void init() {
        List<MiniMapResource> mapResources =
            worldManager.getMapResourcesByGroup(MapGroupType.SINGLE_INSTANCE.getGroupId());
        mapResources.forEach(this::initSingleInstanceMapInfo);
        logger.info("公共副本地图加载完成,数量[{}]", singleInstanceManager.getMapInfoMap().size());
    }

    @Override
    public void enterMap(Player player, int mapId, long sceneId) {
        SingleInstanceMapInfo mapInfo = singleInstanceManager.getMapInfo(mapId);
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
        BaseInstanceMapScene mapScene = getMapScene(mapId, sceneId);
        List<PlayerMapObject> visibleObjects = mapScene.getVisibleObjects();
        Collection<NpcObject> npcList = mapScene.getNpcMap().values();
        Collection<MonsterMapObject> monsterList = mapScene.getMonsterMap().values();
        MapUtil.log(player, mapScene, visibleObjects, npcList, monsterList);
    }

    @Override
    public BaseInstanceMapScene getMapScene(int mapId, long sceneId) {
        return singleInstanceManager.getMapInfo(mapId).getMapScene(sceneId);
    }

    @Override
    public void heartBeat(int mapId) {
        singleInstanceManager.getMapInfoMap().values().forEach(singleInstanceMapInfo -> {
            singleInstanceMapInfo.heatBeat();
        });
    }

    @Override
    public void destroy(int mapId, long sceneId) {
        logger.info("销毁副本[{} {}]", mapId, sceneId);
        singleInstanceManager.getMapInfo(mapId).destroyScene(sceneId);
    }

    @Override
    public void close(int mapId, long sceneId) {
        getMapScene(mapId, sceneId).close();
    }

    @Override
    public long getSceneId(int mapId, long sceneId) {
        return sceneId % singleInstanceManager.getMaxSceneSize();
    }

    public SingleInstanceMapInfo initInstanceMap(int mapId, long playerId) {
        MiniMapResource mapResource = worldManager.getMapResource(mapId);
        SingleInstanceMapInfo mapInfo = SingleInstanceMapInfo.valueOf(mapResource);
        mapInfo.loadSingleInstance(playerId);
        singleInstanceManager.addMapInfo(mapInfo);
        return mapInfo;
    }

    private SingleInstanceMapInfo initSingleInstanceMapInfo(MiniMapResource mapResource) {
        SingleInstanceMapInfo mapInfo = SingleInstanceMapInfo.valueOf(mapResource);
        singleInstanceManager.addMapInfo(mapInfo);
        return mapInfo;
    }
}
