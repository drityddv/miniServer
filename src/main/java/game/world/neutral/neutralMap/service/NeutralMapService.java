package game.world.neutral.neutralMap.service;

import java.util.List;

import game.world.neutral.neutralMap.model.NeutralMapInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.constant.MapGroupType;
import game.map.model.Grid;
import game.map.utils.VisibleUtil;
import game.role.player.model.Player;
import game.user.mapinfo.service.IMapInfoService;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.neutral.neutralGather.service.INeutralMapGatherService;
import game.world.neutral.neutralMap.model.NeutralMapAccountInfo;
import game.world.neutral.neutralMap.model.NeutralMapScene;
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
    private IMapInfoService mapInfoService;

    @Autowired
    private INeutralMapGatherService neutralMapGatherService;

    @Override
    public void init() {
        List<MiniMapResource> mapResources = worldManager.getMapResourcesByGroup(MapGroupType.NEUTRAL_MAP.getGroupId());
        mapResources.forEach(this::initNeutralMapInfo);
        logger.info("中立地图资源与场景初始化完毕,数量[{}]", neutralMapManager.getCommonInfoMap().size());
    }

    @Override
    public boolean canEnterMap(Player player, int mapId) {
        return true;
    }

    @Override
    public void canEnterMapThrow(Player player, int mapId, boolean clientRequest) {

    }

    @Override
    public void enterMap(Player player, int mapId) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene neutralMapScene = mapCommonInfo.getMapScene();
        MiniMapResource mapResource = mapCommonInfo.getMiniMapResource();
        NeutralMapAccountInfo visibleObject = neutralMapScene.getVisibleObject(player.getAccountId());

        if (visibleObject != null) {
            // 继续执行 考虑到断线等因素造成的残影 后期加入心跳 现在直接用新的替换掉
            logger.warn("玩家[{}]存在于地图[{}]中,错误的进入行为", player.getAccountId(), mapId);
        }
        if (visibleObject == null) {
            visibleObject = NeutralMapAccountInfo.valueOf(player, player.getAccountId(), player.getPlayerId());
            visibleObject.init(mapResource.getBornX(), mapResource.getBornY(), false);
        }
        neutralMapScene.enter(player.getAccountId(), visibleObject);
    }

    @Override
    public void leaveMap(Player player) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(player.getCurrentMapId());
        NeutralMapScene neutralMapScene = mapCommonInfo.getMapScene();
        neutralMapScene.leave(player.getAccountId());
    }

    // 这里离开地图不会做后置工作
    @Override
    public void leaveMap(Player player, int newMapId) {
        leaveMap(player);
        player.setChangingMap(false);
        player.setCurrentMapId(0);
        SpringContext.getWorldService().gatewayChangeMap(player, newMapId, false);
    }

    @Override
    public void doMove(Player player, int mapId, Grid targetGrid) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene mapScene = mapCommonInfo.getMapScene();
        NeutralMapAccountInfo sceneVisibleObject = mapScene.getVisibleObject(player.getAccountId());

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
        // StringBuilder sb = new StringBuilder();
        // NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        // NeutralMapScene mapScene = mapCommonInfo.getNeutralMapScene();
        // List<NeutralMapAccountInfo> visibleObjects = mapScene.getVisibleObjects();
        // Collection<NpcVisibleInfo> npcList = mapScene.getNpcMap().values();
        //
        // sb.append(StringUtil.wipePlaceholder("当前地图所处线程[{}]", Thread.currentThread().getName()));
        // sb.append(StringUtil.wipePlaceholder("当前打印地图[{}]", mapId));
        // sb.append(StringUtil.wipePlaceholder("地图内玩家数量[{}]", visibleObjects.size()));
        // visibleObjects.forEach(info -> {
        // sb.append(StringUtil.wipePlaceholder("玩家[{}],坐标[{},{}] 上次移动时间戳[{}]", info.getAccountId(),
        // info.getCurrentX(), info.getCurrentY(), info.getLastMoveAt()));
        // });
        // sb.append(StringUtil.wipePlaceholder("地图内npc数量[{}]", mapScene.getNpcMap().size()));
        // npcList.forEach(npcVisibleInfo -> {
        // sb.append(StringUtil.wipePlaceholder("NPC id[{}],名称[{}] 坐标[{},{}]", npcVisibleInfo.getId(),
        // npcVisibleInfo.getName(), npcVisibleInfo.getCurrentX(), npcVisibleInfo.getCurrentY()));
        // });
        // sb.append(StringUtil.wipePlaceholder("地图内战斗对象数量[{}]", mapScene.getFighterAccountMap().size()));
        // mapScene.getFighterAccountMap().forEach((accountId, fighterAccount) -> {
        // Map<UnitComponentType, IUnitComponent> component =
        // fighterAccount.getCreatureUnit().getComponentContainer().getTypeToComponent();
        // sb.append(StringUtil.wipePlaceholder("战斗对象[{}] 对象id[{}] 对象等级[{}]", accountId,
        // fighterAccount.getCreatureUnit().getId(), fighterAccount.getCreatureUnit().getLevel()));
        // sb.append(" 打印属性 \n");
        // component.forEach((type, iUnitComponent) -> {
        // sb.append(StringUtil.wipePlaceholder("组件种类[{}]", type.name()));
        // if (iUnitComponent instanceof PVPCreatureAttributeComponent) {
        // PVPCreatureAttributeComponent attributeComponent = (PVPCreatureAttributeComponent)iUnitComponent;
        // AttributeUtils.logAttrs(attributeComponent, sb);
        // }
        // });
        // });
        // sb.append(StringUtil.wipePlaceholder("地图内定时器数量[{}]", mapScene.getCommandMap().size()));
        // String logFile = sb.toString();
        // logger.info(logFile);
        // PacketUtil.send(player, SM_LogMessage.valueOf(logFile));
    }

    private NeutralMapScene getMapScene(int mapId) {
        return (NeutralMapScene)neutralMapManager.getCommonInfoMap().get(mapId).getMapScene();
    }

    private void initNeutralMapInfo(MiniMapResource mapResource) {
        NeutralMapInfo commonInfo = NeutralMapInfo.valueOf(mapResource);
        commonInfo.init(mapResource);
        neutralMapManager.addNeutralMapCommonInfo(commonInfo);
        neutralMapGatherService.initNpcResource(commonInfo);
    }

    private NeutralMapScene createNeutralMapScene(MiniMapResource mapResource) {
        NeutralMapScene mapScene = NeutralMapScene.valueOf(mapResource.getMapId());
        logger.info("初始化中立地图场景[{}]", mapScene.getMapId());
        return mapScene;
    }
}
