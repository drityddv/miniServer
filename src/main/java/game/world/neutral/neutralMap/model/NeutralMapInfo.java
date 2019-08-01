package game.world.neutral.neutralMap.model;

import game.map.base.BaseMapInfo;
import game.world.base.resource.MiniMapResource;

/**
 * 中立地图数据信息类
 *
 * @author : ddv
 * @since : 2019/7/3 下午5:57
 */

public class NeutralMapInfo extends BaseMapInfo<NeutralMapScene> {

    public static NeutralMapInfo valueOf(MiniMapResource mapResource) {
        NeutralMapInfo mapCommonInfo = new NeutralMapInfo();
        mapCommonInfo.init(mapResource);
        return mapCommonInfo;
    }

    @Override
    protected void initScene() {
        mapScene = NeutralMapScene.valueOf(mapId, this);
    }

}
