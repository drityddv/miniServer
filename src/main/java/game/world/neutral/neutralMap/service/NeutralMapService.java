package game.world.neutral.neutralMap.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.FighterAccount;
import game.base.game.attribute.util.AttributeUtils;
import game.gm.packet.SM_LogMessage;
import game.miniMap.constant.MapGroupType;
import game.miniMap.model.Grid;
import game.miniMap.utils.VisibleUtil;
import game.miniMap.visible.impl.NpcVisibleInfo;
import game.role.player.model.Player;
import game.user.mapinfo.service.IMapInfoService;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.neutral.neutralGather.service.INeutralMapGatherService;
import game.world.neutral.neutralMap.model.NeutralMapAccountInfo;
import game.world.neutral.neutralMap.model.NeutralMapCommonInfo;
import game.world.neutral.neutralMap.model.NeutralMapScene;
import net.utils.PacketUtil;
import spring.SpringContext;
import utils.StringUtil;

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
        NeutralMapCommonInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene neutralMapScene = mapCommonInfo.getNeutralMapScene();
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
        NeutralMapCommonInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(player.getCurrentMapId());
        NeutralMapScene neutralMapScene = mapCommonInfo.getNeutralMapScene();
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
        NeutralMapCommonInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene mapScene = mapCommonInfo.getNeutralMapScene();
        NeutralMapAccountInfo sceneVisibleObject = mapScene.getVisibleObject(player.getAccountId());

        if (sceneVisibleObject != null) {
            sceneVisibleObject.setTargetX(targetGrid.getX());
            sceneVisibleObject.setTargetY(targetGrid.getY());
            VisibleUtil.doMove(sceneVisibleObject, mapCommonInfo.getBlockResource().getBlockData());
        }
    }

    @Override
    public NeutralMapScene getEnterScene(String accountId, int mapId) {
        return neutralMapManager.getCommonInfoMap().get(mapId).getNeutralMapScene();
    }

    // 因为没有分线概念 这里和上面的区别只是会再检查场景里有没有玩家单位存在
    @Override
    public NeutralMapScene getCurrentScene(String accountId, int mapId) {
        NeutralMapScene mapScene = neutralMapManager.getCommonInfoMap().get(mapId).getNeutralMapScene();
        if (mapScene.isContainPlayer(accountId)) {
            return mapScene;
        } else {
            return null;
        }
    }

    @Override
    public void logMap(Player player, int mapId) {
        StringBuilder sb = new StringBuilder();
        NeutralMapCommonInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene neutralMapScene = mapCommonInfo.getNeutralMapScene();
        List<NeutralMapAccountInfo> visibleObjects = neutralMapScene.getVisibleObjects();
        Collection<NpcVisibleInfo> npcList = neutralMapScene.getNpcMap().values();

        sb.append(StringUtil.wipePlaceholder("当前地图所处线程[{}]", Thread.currentThread().getName()));
        sb.append(StringUtil.wipePlaceholder("当前打印地图[{}]", mapId));
        sb.append(StringUtil.wipePlaceholder("地图内玩家数量[{}]", visibleObjects.size()));
        visibleObjects.forEach(info -> {
            sb.append(StringUtil.wipePlaceholder("玩家[{}],坐标[{},{}] 上次移动时间戳[{}]", info.getAccountId(),
                info.getCurrentX(), info.getCurrentY(), info.getLastMoveAt()));
        });
        sb.append(StringUtil.wipePlaceholder("地图内npc数量[{}]", neutralMapScene.getNpcMap().size()));
        npcList.forEach(npcVisibleInfo -> {
            sb.append(StringUtil.wipePlaceholder("NPC id[{}],名称[{}] 坐标[{},{}]", npcVisibleInfo.getId(),
                npcVisibleInfo.getName(), npcVisibleInfo.getCurrentX(), npcVisibleInfo.getCurrentY()));
        });
        sb.append(StringUtil.wipePlaceholder("地图内战斗对象数量[{}]", neutralMapScene.getFighterAccountMap().size()));
        neutralMapScene.getFighterAccountMap().forEach((accountId, fighterAccount) -> {
            Map<UnitComponentType, IUnitComponent> component =
                fighterAccount.getCreatureUnit().getComponentContainer().getTypeToComponent();
            sb.append(StringUtil.wipePlaceholder("战斗对象[{}] 对象id[{}] 对象等级[{}]", accountId,
                fighterAccount.getCreatureUnit().getId(), fighterAccount.getCreatureUnit().getLevel()));
            sb.append(" 打印属性 \n");
            component.forEach((type, iUnitComponent) -> {
                sb.append(StringUtil.wipePlaceholder("组件种类[{}]", type.name()));
                if (iUnitComponent instanceof PVPCreatureAttributeComponent) {
                    PVPCreatureAttributeComponent attributeComponent = (PVPCreatureAttributeComponent)iUnitComponent;
                    AttributeUtils.logAttrs(attributeComponent, sb);
                }
            });
        });
        sb.append(StringUtil.wipePlaceholder("地图内定时器数量[{}]", neutralMapScene.getCommandMap().size()));
        String logFile = sb.toString();
        logger.info(logFile);
        PacketUtil.send(player, SM_LogMessage.valueOf(logFile));
    }

    @Override
    public void initFighterAccount(Player player, int mapId) {
        NeutralMapScene mapScene = getMapScene(mapId);
        if (!mapScene.isContainPlayer(player.getAccountId())) {
            logger.warn("为玩家[{}]生成战斗对象失败,玩家已经离开地图", player.getAccountId());
            return;
        }
        FighterAccount fighterAccount = FighterAccount.valueOf(player);
        mapScene.fighterEnter(fighterAccount);

    }

    @Override
    public void pkPre(Player player) {
        FighterAccount fighterAccount = SpringContext.getFightService().initForPlayer(player);
        NeutralMapScene mapScene = getMapScene(player.getCurrentMapId());
        mapScene.getFighterAccountMap().putIfAbsent(player.getAccountId(), fighterAccount);
    }

    private NeutralMapScene getMapScene(int mapId) {
        return neutralMapManager.getCommonInfoMap().get(mapId).getNeutralMapScene();
    }

    private void initNeutralMapInfo(MiniMapResource mapResource) {
        NeutralMapCommonInfo commonInfo = NeutralMapCommonInfo.valueOf(mapResource);
        commonInfo.setNeutralMapScene(createNeutralMapScene(mapResource));
        neutralMapManager.addNeutralMapCommonInfo(commonInfo);
        neutralMapGatherService.initNpcResource(commonInfo);
    }

    private NeutralMapScene createNeutralMapScene(MiniMapResource mapResource) {
        NeutralMapScene mapScene = NeutralMapScene.valueOf(mapResource.getMapId());
        logger.info("初始化中立地图场景[{}]", mapScene.getMapId());
        return mapScene;
    }
}
