package game.world.instance.singleIntance.model;

import game.map.base.MapAoiManager;
import game.world.base.service.CreatureManager;
import game.world.instance.base.model.BaseInstanceMapScene;
import game.world.instance.base.model.InstanceMapInfo;
import game.world.instance.base.model.hatch.HatchParam;

/**
 * 单人副本场景
 *
 * @author : ddv
 * @since : 2019/8/1 2:45 PM
 */

public class SingleInstanceMapScene extends BaseInstanceMapScene {

    public SingleInstanceMapScene(int mapId) {
        super(mapId);
    }

    public static SingleInstanceMapScene valueOf(InstanceMapInfo instanceMapInfo) {
        SingleInstanceMapScene mapScene = new SingleInstanceMapScene(instanceMapInfo.getMapId());
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
}
