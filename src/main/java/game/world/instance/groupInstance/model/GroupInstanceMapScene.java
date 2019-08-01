package game.world.instance.groupInstance.model;

import game.map.base.MapAoiManager;
import game.world.base.command.scene.InstanceChangeStageCommand;
import game.world.base.service.CreatureManager;
import game.world.instance.base.model.BaseInstanceMapScene;
import game.world.instance.base.model.InstanceMapInfo;
import game.world.instance.base.model.hatch.HatchParam;
import quartz.job.model.JobEntry;

/**
 * 多人生存副本场景 玩家死亡通关失败
 *
 * @author : ddv
 * @since : 2019/8/1 2:45 PM
 */

public class GroupInstanceMapScene extends BaseInstanceMapScene {
    // 切阶段job
    protected JobEntry stageScheduleJob;

    public GroupInstanceMapScene(int mapId) {
        super(mapId);
    }

    public static GroupInstanceMapScene valueOf(InstanceMapInfo instanceMapInfo) {
        GroupInstanceMapScene mapScene = new GroupInstanceMapScene(instanceMapInfo.getMapId());
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

    // 副本正式开始
    @Override
    protected void instanceStart() {
        super.instanceStart();
        initStageScheduleJob();
    }

    @Override
    public void monsterDeadCallBack() {

    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void playerDeadCallBack() {
        stageScheduleJob.cancel();
        failedSettlement();
    }

    private void initStageScheduleJob() {
        stageScheduleJob = JobEntry.newSceneRateJob(instanceResource.getChangeStageDelay(),
            instanceResource.getTotalStage(), InstanceChangeStageCommand.valueOf(mapId, sceneId));
        stageScheduleJob.schedule();
    }
}
