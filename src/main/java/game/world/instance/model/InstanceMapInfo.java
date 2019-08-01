package game.world.instance.model;

import java.util.HashMap;
import java.util.Map;

import game.map.base.BaseMapInfo;
import game.world.base.resource.MiniMapResource;
import game.world.instance.resource.InstanceResource;
import game.world.instance.service.InstanceManager;

/**
 * @author : ddv
 * @since : 2019/7/30 5:35 PM
 */

public class InstanceMapInfo extends BaseMapInfo<InstanceMapScene> {
    // 副本信息
    private InstanceResource instanceResource;

    /**
     * 单人副本 sceneId == playerId 公共副本用父类的
     */
    private Map<Long, InstanceMapScene> singleInstanceMap = new HashMap<>();

    public static InstanceMapInfo valueOf(MiniMapResource mapResource) {
        InstanceMapInfo mapInfo = new InstanceMapInfo();
        mapInfo.instanceResource = InstanceManager.getInstance().getInstanceResourceByMapId(mapResource.getMapId());
        mapInfo.init(mapResource);
        return mapInfo;
    }

    @Override
    protected void initScene() {
        mapScene = InstanceMapScene.valueOf(this);
    }

    public InstanceResource getInstanceResource() {
        return instanceResource;
    }

    @Override
    public InstanceMapScene getMapScene() {
        return super.getMapScene();
    }

    @Override
    public InstanceMapScene getMapScene(long sceneId) {
        if (sceneId == this.mapId) {
            return getMapScene();
        }
        return singleInstanceMap.get(sceneId);
    }

    public boolean containsScene(long sceneId) {
        if (this.mapScene.getSceneId() == sceneId) {
            return true;
        }
        if (this.singleInstanceMap.containsKey(sceneId)) {
            return true;
        }
        return false;
    }

    public InstanceMapScene loadSingleInstance(long sceneId) {
        InstanceMapScene mapScene = InstanceMapScene.valueOf(this);
        mapScene.setSceneId(sceneId);
        singleInstanceMap.put(sceneId, mapScene);
        return mapScene;
    }
}
