package game.world.neutralMap.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.gm.packet.SM_LogMessage;
import game.miniMap.handler.MapGroupType;
import game.miniMap.model.Grid;
import game.user.mapinfo.service.IMapInfoService;
import game.user.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.neutralMap.model.NeutralMapAccountInfo;
import game.world.neutralMap.model.NeutralMapCommonInfo;
import game.world.neutralMap.model.NeutralMapScene;
import net.utils.PacketUtil;
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

    @Override
    public void init() {
        List<MiniMapResource> mapResources = worldManager.getMapResourcesByGroup(MapGroupType.NEUTRAL_MAP.getGroupId());
        mapResources.forEach(this::initNeutralMapInfo);
        logger.info("中立地图资源与场景初始化完毕,数量[{}]", neutralMapManager.getCommonInfoMap().size());
    }

    @Override
    public boolean canEnterMap(Player player, int mapId) {
        return false;
    }

    @Override
    public void canEnterMapThrow(Player player, int mapId, boolean clientRequest) {

    }

    @Override
    public void enterMap(Player player, int mapId) {
        NeutralMapCommonInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene neutralMapScene = mapCommonInfo.getNeutralMapScene();
        NeutralMapAccountInfo visibleObject = neutralMapScene.getVisibleObject(player.getAccountId());

        if (visibleObject != null) {
            // 继续执行 考虑到断线等因素造成的残影 后期加入心跳 现在直接用新的替换掉
            logger.warn("玩家[{}]存在于地图[{}]中,错误的进入行为", player.getAccountId(), mapId);
        }
        if (visibleObject == null) {
            visibleObject = NeutralMapAccountInfo.valueOf(player.getAccountId(), player.getPlayerId());
        }
        neutralMapScene.enter(player.getAccountId(), visibleObject);
    }

    @Override
    public void leaveMap(Player player) {

    }

    @Override
    public void leaveMap(Player player, int newMapId) {

    }

    @Override
    public void doMove(Player player, int mapId, Grid currentGrid, Grid targetGrid) {

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
        sb.append(StringUtil.wipePlaceholder("当前地图所处线程[{}]", Thread.currentThread().getName()) + '\n');
        sb.append(StringUtil.wipePlaceholder("当前打印地图[{}]", mapId) + '\n');
        sb.append(StringUtil.wipePlaceholder("地图内玩家数量[{}]", neutralMapScene.getVisibleObjects().size()) + '\n');
        sb.append(StringUtil.wipePlaceholder("地图内npc数量[{}]", neutralMapScene.getNpcMap().size()) + '\n');
        sb.append(StringUtil.wipePlaceholder("地图内战斗对象数量[{}]", neutralMapScene.getFighterAccountMap().size()) + '\n');
        sb.append(StringUtil.wipePlaceholder("地图内定时器数量[{}]", neutralMapScene.getCommandMap().size()) + '\n');
        String logFile = sb.toString();
        logger.info(logFile);
        PacketUtil.send(player, SM_LogMessage.valueOf(logFile));
    }

    private void initNeutralMapInfo(MiniMapResource mapResource) {
        NeutralMapCommonInfo commonInfo = NeutralMapCommonInfo.valueOf(mapResource);
        commonInfo.setNeutralMapScene(createNeutralMapScene(mapResource));
        neutralMapManager.addNeutralMapCommonInfo(commonInfo);
    }

    private NeutralMapScene createNeutralMapScene(MiniMapResource mapResource) {
        NeutralMapScene mapScene = NeutralMapScene.valueOf(mapResource.getMapId());
        logger.info("初始化中立地图场景[{}]", mapScene.getMapId());
        return mapScene;
    }
}
