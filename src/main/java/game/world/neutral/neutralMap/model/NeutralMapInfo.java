package game.world.neutral.neutralMap.model;

import game.map.base.BaseMapInfo;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;

/**
 * 中立地图基本数据信息
 *
 * @author : ddv
 * @since : 2019/7/3 下午5:57
 */

public class NeutralMapInfo extends BaseMapInfo<NeutralMapScene> {

    @Override
    public void init(MiniMapResource mapResource) {
        mapId = mapResource.getMapId();
        miniMapResource = mapResource;
        blockResource = WorldManager.getInstance().getBlockResource(mapResource.getMapDataConfigId());
    }

    public static NeutralMapInfo valueOf(MiniMapResource mapResource) {
        NeutralMapInfo mapCommonInfo = new NeutralMapInfo();
        mapCommonInfo.init(mapResource);
        return mapCommonInfo;
    }

    @Override
    public NeutralMapScene getMapScene() {
        return mapScene;
    }
}
