package game.world.neutral.neutralMap.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.constant.MapGroupType;
import game.map.model.Grid;
import game.map.utils.VisibleUtil;
import game.map.visible.PlayerVisibleMapInfo;
import game.map.visible.impl.NpcVisibleInfo;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.neutral.neutralGather.service.INpcService;
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

    @Autowired
    private INpcService neutralMapGatherService;

    @Override
    public void init() {
        List<MiniMapResource> mapResources = worldManager.getMapResourcesByGroup(MapGroupType.NEUTRAL_MAP.getGroupId());
        mapResources.forEach(this::initNeutralMapInfo);
        logger.info("中立地图资源与场景初始化完毕,数量[{}]", neutralMapManager.getCommonInfoMap().size());
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
        PlayerVisibleMapInfo visibleObject = neutralMapScene.getVisibleObject(player.getAccountId());

        if (visibleObject != null) {
            // 继续执行 考虑到断线等因素造成的残影 后期加入心跳 现在直接用新的替换掉
            logger.warn("玩家[{}]存在于地图[{}]中,错误的进入行为", player.getAccountId(), mapId);
        }
        if (visibleObject == null) {
            visibleObject = PlayerVisibleMapInfo.valueOf(player);
            visibleObject.init(mapResource.getBornX(), mapResource.getBornY());
        }
        neutralMapScene.enter(player.getAccountId(), visibleObject);
    }

    @Override
    public void leaveMap(Player player) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(player.getCurrentMapId());
        NeutralMapScene neutralMapScene = mapCommonInfo.getMapScene();
        neutralMapScene.leave(player.getAccountId());
    }

    @Override
    public void doMove(Player player, Grid targetGrid) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(player.getCurrentMapId());
        NeutralMapScene mapScene = mapCommonInfo.getMapScene();
        PlayerVisibleMapInfo sceneVisibleObject = mapScene.getVisibleObject(player.getAccountId());

        if (sceneVisibleObject != null) {
            sceneVisibleObject.setTargetX(targetGrid.getX());
            sceneVisibleObject.setTargetY(targetGrid.getY());
            VisibleUtil.doMove(sceneVisibleObject, mapCommonInfo.getBlockResource().getBlockData());
        }
    }

    // 因为没有分线概念 这里和上面的区别只是会再检查场景里有没有玩家单位存在
    @Override
    public NeutralMapScene getCurrentScene(Player player) {
        NeutralMapScene mapScene = neutralMapManager.getCommonInfoMap().get(player.getCurrentMapId()).getMapScene();
        if (mapScene.isContainPlayer(player.getAccountId())) {
            return mapScene;
        } else {
            return null;
        }
    }

    @Override
    public void logMap(Player player, int mapId) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene mapScene = mapCommonInfo.getMapScene();
        List<PlayerVisibleMapInfo> visibleObjects = mapScene.getVisibleObjects();
        Collection<NpcVisibleInfo> npcList = mapScene.getNpcMap().values();
        MapUtil.log(player, mapScene, visibleObjects, npcList);
    }

    private void initNeutralMapInfo(MiniMapResource mapResource) {
        NeutralMapInfo commonInfo = NeutralMapInfo.valueOf(mapResource);
        commonInfo.init(mapResource);
        commonInfo.getMapScene().initNpc(SpringContext.getNpcManager().getNpcByMapId(commonInfo.getMapId()));
        neutralMapManager.addNeutralMapCommonInfo(commonInfo);
    }

    private NeutralMapInfo getMapInfo(int mapId) {
        return neutralMapManager.getNeutralMapCommonInfo(mapId);
    }

}
