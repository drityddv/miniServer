package game.world.mainCity.model;

import game.map.base.BaseMapInfo;
import game.world.base.resource.MiniMapResource;

/**
 * 主城信息
 *
 * @author : ddv
 * @since : 2019/7/10 下午9:22
 */

public class MainCityMapInfo extends BaseMapInfo<MainCitySceneScene> {

    public static MainCityMapInfo valueOf(MiniMapResource mapResource) {
        MainCityMapInfo mapCommonInfo = new MainCityMapInfo();
        mapCommonInfo.init(mapResource);
        return mapCommonInfo;
    }

    @Override
    protected void initScene() {
        mapScene = MainCitySceneScene.valueOf(mapId);
    }

    @Override
    public MainCitySceneScene getMapScene() {
        return mapScene;
    }
}
