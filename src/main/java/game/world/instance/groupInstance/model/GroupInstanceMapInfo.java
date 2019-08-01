package game.world.instance.groupInstance.model;

import game.world.base.resource.MiniMapResource;
import game.world.instance.base.model.BaseInstanceMapScene;
import game.world.instance.base.model.InstanceMapInfo;
import game.world.instance.base.service.InstanceManager;

/**
 * @author : ddv
 * @since : 2019/7/30 5:35 PM
 */

public class GroupInstanceMapInfo extends InstanceMapInfo {

    public static GroupInstanceMapInfo valueOf(MiniMapResource mapResource) {
        GroupInstanceMapInfo mapInfo = new GroupInstanceMapInfo();
        mapInfo.instanceResource = InstanceManager.getInstance().getInstanceResourceByMapId(mapResource.getMapId());
        mapInfo.init(mapResource);
        mapInfo.initScene();
        return mapInfo;
    }

    @Override
    protected void initScene() {
        mapScene = GroupInstanceMapScene.valueOf(this);
    }

    @Override
    public BaseInstanceMapScene getMapScene(long playerId) {
        return getMapScene();
    }

}
