package game.world.neutral.neutralMap.model;

import game.world.base.resource.MapBlockResource;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;

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

    private MiniMapResource miniMapResource;

    private MapBlockResource blockResource;

    public static NeutralMapCommonInfo valueOf(MiniMapResource mapResource) {
        NeutralMapCommonInfo info = new NeutralMapCommonInfo();
        info.mapId = mapResource.getMapId();
        info.miniMapResource = mapResource;
        info.blockResource = WorldManager.getInstance().getBlockResource(mapResource.getMapDataConfigId());
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

    public MiniMapResource getMiniMapResource() {
        return miniMapResource;
    }

    public MapBlockResource getBlockResource() {
        return blockResource;
    }
}
