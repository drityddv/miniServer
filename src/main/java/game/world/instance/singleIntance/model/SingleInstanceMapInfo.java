package game.world.instance.singleIntance.model;

import java.util.HashMap;
import java.util.Map;

import game.world.base.resource.MiniMapResource;
import game.world.instance.base.model.BaseInstanceMapScene;
import game.world.instance.base.model.InstanceMapInfo;
import game.world.instance.base.service.InstanceManager;

/**
 * @author : ddv
 * @since : 2019/7/30 5:35 PM
 */

public class SingleInstanceMapInfo extends InstanceMapInfo {
    /**
     * 单人副本 playerId --sceneId
     */
    private Map<Long, SingleInstanceMapScene> singleInstanceMap = new HashMap<>();

    public static SingleInstanceMapInfo valueOf(MiniMapResource mapResource) {
        SingleInstanceMapInfo mapInfo = new SingleInstanceMapInfo();
        mapInfo.instanceResource = InstanceManager.getInstance().getInstanceResourceByMapId(mapResource.getMapId());
        mapInfo.init(mapResource);
        return mapInfo;
    }

    @Override
    protected void initScene() {

    }

    @Override
    public BaseInstanceMapScene getMapScene() {
        return null;
    }

    @Override
    public BaseInstanceMapScene getMapScene(long playerId) {
        return singleInstanceMap.get(playerId);
    }

    public void heatBeat() {
        Map<Long, BaseInstanceMapScene> temp = new HashMap<>(singleInstanceMap);
        temp.forEach((aLong, instanceMapScene) -> {
            instanceMapScene.tryDestroy();
        });
    }

    public SingleInstanceMapScene loadSingleInstance(long playerId) {
        SingleInstanceMapScene mapScene = SingleInstanceMapScene.valueOf(this);
        mapScene.setSceneId(playerId);
        singleInstanceMap.put(playerId, mapScene);
        return mapScene;
    }

    public void destroyScene(long sceneId) {
        SingleInstanceMapScene mapScene = singleInstanceMap.get(sceneId);
        mapScene.close();
        singleInstanceMap.remove(sceneId);
    }
}
