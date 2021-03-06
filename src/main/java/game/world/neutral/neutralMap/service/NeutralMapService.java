package game.world.neutral.neutralMap.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import game.world.neutral.neutralMap.model.NeutralMapInfo;
import game.world.neutral.neutralMap.model.NeutralMapScene;
import game.world.utils.MapUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:41
 */
@Component
public class NeutralMapService implements INeutralMapService {

    private static final Logger logger = LoggerFactory.getLogger(NeutralMapService.class);

    @Autowired
    private NeutralMapManager neutralMapManager;

    @Autowired
    private WorldManager worldManager;

    @Override
    public void init() {
        initMapInfo();
    }

    private void initMapInfo() {
        List<MiniMapResource> mapResources = worldManager.getMapResourcesByGroup(MapGroupType.NEUTRAL_MAP.getGroupId());
        mapResources.forEach(this::initNeutralMapInfo);
        logger.info("中立地图资源与场景初始化结束,数量[{}]", neutralMapManager.getCommonInfoMap().size());
    }

    @Override
    public boolean canEnterMap(Player player, int mapId) {
        NeutralMapInfo mapInfo = getMapInfo(mapId);
        MiniMapResource mapResource = mapInfo.getMiniMapResource();
        return player.getLevel() >= mapResource.getMinLevel() && player.getLevel() <= mapResource.getMaxLevel();
    }

    @Override
    public void enterMap(Player player, int mapId) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene neutralMapScene = mapCommonInfo.getMapScene();
        MiniMapResource mapResource = mapCommonInfo.getMiniMapResource();

        PlayerMapObject visibleObject = PlayerMapObject.valueOf(player, mapId, mapId);
        visibleObject.init(mapResource.getBornX(), mapResource.getBornY());

        neutralMapScene.enter(player.getPlayerId(), visibleObject);
    }

    @Override
    public void leaveMap(Player player) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(player.getCurrentMapId());
        NeutralMapScene neutralMapScene = mapCommonInfo.getMapScene();
        neutralMapScene.leave(player.getPlayerId());
    }

    @Override
    public void doMove(Player player, Grid targetGrid) {
        NeutralMapScene scene = getMapScene(player.getCurrentMapId());
        scene.move(player.getPlayerId(), targetGrid);
    }

    @Override
    public NeutralMapScene getCurrentScene(Player player) {
        NeutralMapScene mapScene = neutralMapManager.getCommonInfoMap().get(player.getCurrentMapId()).getMapScene();
        if (mapScene.isContainPlayer(player.getPlayerId())) {
            return mapScene;
        } else {
            return null;
        }
    }

    @Override
    public void logMap(Player player, int mapId) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene mapScene = mapCommonInfo.getMapScene();
        List<PlayerMapObject> visibleObjects = mapScene.getVisibleObjects();
        Collection<NpcObject> npcList = mapScene.getNpcMap().values();
        Collection<MonsterMapObject> monsterList = mapScene.getMonsterMap().values();
        MapUtil.log(player, mapScene, visibleObjects, npcList, monsterList);

    }

    @Override
    public void heartBeat(int mapId) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene mapScene = mapCommonInfo.getMapScene();
        List<PlayerMapObject> visibleObjects = mapScene.getVisibleObjects();
        for (PlayerMapObject visibleObject : visibleObjects) {
            if (!SpringContext.getPlayerService().isPlayerOnline(visibleObject.getAccountId())) {
                mapScene.leave(visibleObject.getId());
            }
        }
    }

    @Override
    public Map<Long, PlayerMapObject> getVisibleObjects(int mapId) {
        return getMapInfo(mapId).getMapScene().getPlayerMap();
    }

    @Override
    public Map<Long, MonsterMapObject> getMonsterObjects(int mapId) {
        return getMapInfo(mapId).getMapScene().getMonsterMap();
    }

    @Override
    public NeutralMapScene getMapScene(int mapId) {
        return getMapInfo(mapId).getMapScene();
    }

    @Override
    public void test(int mapId, Map<String, Object> param) {

    }

    @Override
    public void broadcast(int mapId, Grid currentGrid) {
        getMapScene(mapId).broadcast(currentGrid);
    }

    private void initNeutralMapInfo(MiniMapResource mapResource) {
        NeutralMapInfo commonInfo = NeutralMapInfo.valueOf(mapResource);
        NeutralMapScene mapScene = commonInfo.getMapScene();
        mapScene.initNpc(SpringContext.getCreatureManager().getNpcByMapId(commonInfo.getMapId()));
        mapScene.initMonster(SpringContext.getCreatureManager().getCreatureResourceByMapId(commonInfo.getMapId()));
        neutralMapManager.addNeutralMapCommonInfo(commonInfo);
    }

    private NeutralMapInfo getMapInfo(int mapId) {
        return neutralMapManager.getNeutralMapCommonInfo(mapId);
    }

}
