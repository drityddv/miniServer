package game.world.instance.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.util.ExecutorUtils;
import game.base.item.base.model.AbstractItem;
import game.gm.packet.SM_LogMessage;
import game.map.base.AbstractMovableScene;
import game.map.base.MapAoiManager;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.world.base.command.player.AddItemToPackCommand;
import game.world.base.command.scene.EnterMapCommand;
import game.world.base.command.scene.InstanceCloseCommand;
import game.world.base.service.CreatureManager;
import game.world.instance.model.hatch.HatchParam;
import game.world.instance.resource.InstanceResource;
import net.utils.PacketUtil;
import quartz.job.model.JobEntry;
import spring.SpringContext;
import utils.StringUtil;

/**
 * 副本地图
 *
 * @author : ddv
 * @since : 2019/7/30 5:15 PM
 */

// mapId - scene

public class InstanceMapScene extends AbstractMovableScene<PlayerMapObject> {
    private static final Logger logger = LoggerFactory.getLogger(InstanceMapScene.class);
    // 是否需要刷怪
    private boolean needHatchMonster = true;
    // 副本是否正式开始
    private boolean start = false;
    private LocalTime startAt;
    // 关闭副本调度作业 注意不是销毁!!!
    private JobEntry closeScheduleJob;
    // 当前阶段
    private int currentStage = 1;
    // 阶段 -- 怪物孵化器
    private Map<Integer, HatchParam> stageHatchMap = new HashMap<>();

    private InstanceResource instanceResource;

    public InstanceMapScene(int mapId) {
        super(mapId);
    }

    public static InstanceMapScene valueOf(InstanceMapInfo instanceMapInfo) {
        InstanceMapScene mapScene = new InstanceMapScene(instanceMapInfo.getMapId());
        mapScene.aoiManager = MapAoiManager.valueOf(instanceMapInfo);
        mapScene.baseMapInfo = instanceMapInfo;
        mapScene.instanceResource = instanceMapInfo.getInstanceResource();
        int totalStage = instanceMapInfo.getInstanceResource().getTotalStage();
        for (int i = 0; i < totalStage; i++) {
            mapScene.instanceResource.getStageHatchResourceMap().forEach((stage, hatchResourceConfigId) -> {
                mapScene.stageHatchMap.put(stage,
                    HatchParam.valueOf(CreatureManager.getInstance().getHatchResource(hatchResourceConfigId)));
            });
        }
        return mapScene;
    }

    @Override
    public void enter(long playerId, PlayerMapObject object) {
        hatchMonsters();
        super.enter(playerId, object);
        if (!start) {
            instanceStart();
        }
    }

    // 副本正式开始
    private void instanceStart() {
        logger.info("副本[{}]正式开始!", mapId);
        closeScheduleJob =
            JobEntry.newSceneDelayJob(instanceResource.getMaxTime(), InstanceCloseCommand.valueOf(mapId, sceneId));
        closeScheduleJob.schedule();
        start = true;
        startAt = LocalTime.now();
    }

    @Override
    public void leave(long playerId) {
        super.leave(playerId);
        needHatchMonster = true;
    }

    // 销毁副本实例
    public void tryDestroy() {
        if (mapId == sceneId) {
            return;
        }
        if (ChronoUnit.MINUTES.between(startAt, LocalTime.now()) >= 1) {
            close();
            SpringContext.getInstanceService().destroy(mapId, sceneId);
        }
    }

    // 通往下一关
    public void passStage() {
        if (currentStage == instanceResource.getTotalStage()) {
            // 通关
            passInstance();
            return;
        }
        clearMonster();
        currentStage++;
        hatchMonsters();
    }

    private void clearMonster() {
        monsterMap.clear();
        aoiManager.removeMonsters();
        needHatchMonster = true;
    }

    public void hatchMonsters() {
        if (!needHatchMonster) {
            return;
        }
        List<MonsterMapObject> monsterObjects = stageHatchMap.get(currentStage).hatch();
        monsterObjects.forEach(monster -> {
            monsterMap.put(monster.getId(), monster);
            aoiManager.registerUnits(monster);
        });
        for (PlayerMapObject mapObject : playerMap.values()) {
            PacketUtil.send(mapObject.getPlayer(), SM_LogMessage
                .valueOf(StringUtil.wipePlaceholder("当前波数[{}] 刷怪数量[{}]", currentStage, monsterObjects.size())));
        }

    }

    private boolean allMonsterDead() {
        return monsterMap.values().stream()
            .allMatch(monsterMapObject -> monsterMapObject.getFighterAccount().getCreatureUnit().isDead());
    }

    // 怪物死亡回调
    public void stageCheck() {
        if (!allMonsterDead()) {
            return;
        }
        if (currentStage == instanceResource.getTotalStage()) {
            passInstance();
            return;
        }
        passStage();
    }

    private void passInstance() {
        // 发奖
        rewardPlayers();
        // 关副本
        close();
    }

    // 关闭副本
    public void close() {
        logger.info("副本[{}]关闭,重置相关信息", mapId);
        clearMonster();
        kickPlayers();
        cancelSchedule();
        reset();
    }

    private void reset() {
        currentStage = 1;
        needHatchMonster = true;
        start = false;
    }

    private void kickPlayers() {
        playerMap.values().forEach(playerMapObject -> {
            ExecutorUtils.submit(EnterMapCommand.valueOf(playerMapObject.getPlayer(), 4, 0));
        });
        playerMap.clear();
    }

    // 发奖
    private void rewardPlayers() {
        long dropConfigId = instanceResource.getDropConfigId();
        playerMap.values().forEach(playerMapObject -> {
            List<AbstractItem> rewardItems = SpringContext.getItemService().createItemsByDropConfig(dropConfigId);
            ExecutorUtils.submit(AddItemToPackCommand.valueOf(playerMapObject.getPlayer(), rewardItems));
        });
    }

    private void cancelSchedule() {
        closeScheduleJob.cancel();
    }
}
