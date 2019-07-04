package game.world.neutralMap.model;

import game.world.base.resource.MiniMapResource;

/**
 * 种类地图的公共信息类 公共信息可以在这里存
 *
 * @author : ddv
 * @since : 2019/7/3 下午5:57
 */

public class NeutralMapCommonInfo {

    private int mapId;
    // 无分线逻辑 每张地图只会有一张
    private NeutralMapScene neutralMapScene;

    public static NeutralMapCommonInfo valueOf(MiniMapResource mapResource) {
        NeutralMapCommonInfo info = new NeutralMapCommonInfo();
        info.mapId = mapResource.getMapId();
        return info;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public NeutralMapScene getNeutralMapScene() {
        return neutralMapScene;
    }

    public void setNeutralMapScene(NeutralMapScene neutralMapScene) {
        this.neutralMapScene = neutralMapScene;
    }
}
