package game.world.instance.base.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.MessageEnum;
import game.base.executor.util.ExecutorUtils;
import game.base.item.base.model.AbstractItem;
import game.base.message.exception.RequestException;
import game.gm.packet.SM_LogMessage;
import game.map.base.AbstractMovableScene;
import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.world.base.command.player.AddItemToPackCommand;
import game.world.base.command.scene.EnterMapCommand;
import game.world.base.command.scene.InstanceCloseCommand;
import game.world.instance.base.constant.InstanceConst;
import game.world.instance.base.model.hatch.HatchParam;
import game.world.instance.base.resource.InstanceResource;
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

public abstract class BaseInstanceMapScene extends AbstractMovableScene<PlayerMapObject> {
    // 副本是否正式开始
    protected boolean start = false;
    // 副本是否结束
    protected boolean end;
    // 副本开始时间
    protected LocalTime startAt;
    // 副本开始时间
    protected LocalTime endAt;
    // 重置时间
    protected LocalTime resetAt;
    // 延迟关闭副本调度作业
    protected JobEntry closeScheduleJob;
    // 当前阶段
    protected int currentStage = 1;
    // 阶段 -- 怪物孵化器
    protected Map<Integer, HatchParam> stageHatchMap = new HashMap<>();

    protected InstanceResource instanceResource;

    public BaseInstanceMapScene(int mapId) {
        super(mapId);
    }

    @Override
    public void enter(long playerId, PlayerMapObject object) {
        if (end) {
            RequestException.throwException(MessageEnum.INSTANCE_END);
        }
        if (!start) {
            instanceStart();
        }
        super.enter(playerId, object);
    }

    @Override
    public void leave(long playerId) {
        super.leave(playerId);
    }

    @Override
    public void move(long playerId, Grid targetGrid) {
        if (!end) {
            super.move(playerId, targetGrid);
        }
    }

    // 副本正式开始
    protected void instanceStart() {
        logger.info("副本[{}] id[{}]正式开始!", mapId, sceneId);
        hatchMonsters();
        start = true;
        startAt = LocalTime.now();
        end = false;
        endAt = null;
    }

    // 销毁副本实例 副本上一次开启距今超过五分钟清除
    public void tryDestroy() {
        if (ChronoUnit.MINUTES.between(resetAt, LocalTime.now()) >= InstanceConst.INSTANCE_DESTROY_MINUTES) {
            SpringContext.getInstanceService().destroy(mapId, sceneId);
        }
    }

    // 通往下一关
    public void changeStage() {
        if (currentStage == instanceResource.getTotalStage()) {
            // 通关
            passSettlement();
            return;
        }
        clearMonster();
        currentStage++;
        hatchMonsters();
    }

    private void clearMonster() {
        monsterMap.clear();
        aoiManager.removeMonsters();
    }

    public void hatchMonsters() {
        List<MonsterMapObject> monsterObjects = stageHatchMap.get(currentStage).hatch();
        monsterObjects.forEach(monsterMapObject -> {
            monsterMapObject.getFighterAccount().getCreatureUnit().setSceneId(sceneId);
        });

        for (PlayerMapObject mapObject : playerMap.values()) {
            PacketUtil.send(mapObject.getPlayer(), SM_LogMessage
                .valueOf(StringUtil.wipePlaceholder("当前波数[{}] 刷怪数量[{}]", currentStage, monsterObjects.size())));
        }

        monsterObjects.forEach(monster -> {
            monsterMap.put(monster.getId(), monster);
            aoiManager.registerUnits(monster);
        });

    }

    private boolean allMonsterDead() {
        return monsterMap.values().stream()
            .allMatch(monsterMapObject -> monsterMapObject.getFighterAccount().getCreatureUnit().isDead());
    }

    // 怪物死亡回调
    public void monsterDeadCallBack() {
        if (ableSettlement()) {
            passSettlement();
            return;
        }
        if (allMonsterDead()) {
            changeStage();
        }
    }

    private boolean ableSettlement() {
        if (!allMonsterDead()) {
            return false;
        }
        if (currentStage != instanceResource.getTotalStage()) {
            return false;
        }
        return true;
    }

    // 成功通过结算
    protected void passSettlement() {
        end = true;
        rewardPlayers();
        // 延迟关闭副本
        closeScheduleJob =
            JobEntry.newSceneDelayJob(InstanceConst.DELAY_CLOSE_INSTANCE, InstanceCloseCommand.valueOf(mapId, sceneId));
        closeScheduleJob.schedule();
    }

    protected void failedSettlement() {
        end = true;
        // 延迟关闭副本
        playerMap.values().forEach(playerMapObject -> {
            PacketUtil.send(playerMapObject.getPlayer(), MessageEnum.Instance_Failed);
        });

        closeScheduleJob =
            JobEntry.newSceneDelayJob(InstanceConst.DELAY_CLOSE_INSTANCE, InstanceCloseCommand.valueOf(mapId, sceneId));
        closeScheduleJob.schedule();
    }

    // 关闭副本
    public void close() {
        logger.info("副本[{}]关闭,重置相关信息", mapId);
        endAt = LocalTime.now();
        clearMonster();
        kickPlayers();
        reset();
    }

    // 重置副本信息
    private void reset() {
        currentStage = 1;
        start = false;
        startAt = null;
        end = false;
        resetAt = LocalTime.now();
    }

    // 踢人
    private void kickPlayers() {
        playerMap.values().forEach(playerMapObject -> {
            ExecutorUtils.submit(EnterMapCommand.valueOfMainCity(playerMapObject.getPlayer()));
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

    public void playerDeadCallBack() {}

    public int getCurrentPlayerSize() {
        return playerMap.size();
    }

}
