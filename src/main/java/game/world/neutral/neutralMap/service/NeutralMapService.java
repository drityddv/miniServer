package game.world.neutral.neutralMap.service;

import static org.quartz.DateBuilder.futureDate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.effect.model.BaseBuffEffect;
import game.base.executor.command.impl.scene.impl.rate.SceneHeartBeatCommand;
import game.map.constant.MapGroupType;
import game.map.model.Grid;
import game.map.visible.PlayerVisibleMapObject;
import game.map.visible.impl.MonsterVisibleMapObject;
import game.map.visible.impl.NpcVisibleObject;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.neutral.neutralMap.model.NeutralMapInfo;
import game.world.neutral.neutralMap.model.NeutralMapScene;
import game.world.utils.MapUtil;
import scheduler.constant.JobGroupEnum;
import scheduler.job.common.scene.SceneHeartBeatRateJob;
import spring.SpringContext;
import utils.snow.IdUtil;

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
        initHeartBeat();
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
        PlayerVisibleMapObject visibleObject = neutralMapScene.getPlayerObject(player.getPlayerId());

        if (visibleObject != null) {
            // 继续执行 考虑到断线等因素造成的残影 后期加入心跳 现在直接用新的替换掉
            logger.warn("玩家[{}]存在于地图[{}]中,错误的进入行为", player.getAccountId(), mapId);
        }
        if (visibleObject == null) {
            visibleObject = PlayerVisibleMapObject.valueOf(player, mapId);
            visibleObject.init(mapResource.getBornX(), mapResource.getBornY());
        }
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
        List<PlayerVisibleMapObject> visibleObjects = mapScene.getVisibleObjects();
        Collection<NpcVisibleObject> npcList = mapScene.getNpcMap().values();
        Collection<MonsterVisibleMapObject> monsterList = mapScene.getMonsterMap().values();
        MapUtil.log(player, mapScene, visibleObjects, npcList, monsterList, mapScene.getBuffEffectMap().values());

    }

    @Override
    public void heartBeat(int mapId) {
        NeutralMapInfo mapCommonInfo = neutralMapManager.getNeutralMapCommonInfo(mapId);
        NeutralMapScene mapScene = mapCommonInfo.getMapScene();
        List<PlayerVisibleMapObject> visibleObjects = mapScene.getVisibleObjects();
        for (PlayerVisibleMapObject visibleObject : visibleObjects) {
            if (!SpringContext.getPlayerService().isPlayerOnline(visibleObject.getAccountId())) {
                mapScene.leave(visibleObject.getId());
            }
        }
    }

    @Override
    public Map<Long, PlayerVisibleMapObject> getVisibleObjects(int mapId) {
        return getMapInfo(mapId).getMapScene().getPlayerMap();
    }

    @Override
    public Map<Long, MonsterVisibleMapObject> getMonsterObjects(int mapId) {
        return getMapInfo(mapId).getMapScene().getMonsterMap();
    }

    @Override
    public Map<Long, BaseBuffEffect> getBuffEffects(int mapId) {
        return getMapScene(mapId).getBuffEffectMap();
    }

    @Override
    public NeutralMapScene getMapScene(int mapId) {
        return getMapInfo(mapId).getMapScene();
    }

    private void initNeutralMapInfo(MiniMapResource mapResource) {
        NeutralMapInfo commonInfo = NeutralMapInfo.valueOf(mapResource);
        NeutralMapScene mapScene = commonInfo.getMapScene();
        mapScene.initNpc(SpringContext.getCreatureManager().getNpcByMapId(commonInfo.getMapId()));
        mapScene.initMonster(SpringContext.getCreatureManager().getCreatureResourceByMapId(commonInfo.getMapId()));
        mapScene.initAoiManager();
        neutralMapManager.addNeutralMapCommonInfo(commonInfo);
    }

    private NeutralMapInfo getMapInfo(int mapId) {
        return neutralMapManager.getNeutralMapCommonInfo(mapId);
    }

    private void initHeartBeat() {
        Set<Integer> mapIdSet = neutralMapManager.getCommonInfoMap().keySet();
        for (Integer mapId : mapIdSet) {
            String groupName = JobGroupEnum.SCENE_COMMON_RATE.name();
            String jobName = IdUtil.getLongId() + "";
            String triggerName = IdUtil.getLongId() + "";

            SceneHeartBeatCommand command = SceneHeartBeatCommand.valueOf(mapId, 1000 * 60 * 2, 0);

            JobDetail jobDetail =
                JobBuilder.newJob(SceneHeartBeatRateJob.class).withIdentity(jobName, groupName).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(command.getDelay())
                    .repeatForever())
                .startAt((futureDate(60, DateBuilder.IntervalUnit.SECOND))).forJob(jobDetail.getKey()).build();

            jobDetail.getJobDataMap().put("command", command);
            SpringContext.getQuartzService().addJob(jobDetail, trigger);
        }

    }

}
