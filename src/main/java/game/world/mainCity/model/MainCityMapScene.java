package game.world.mainCity.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.base.AbstractMovableScene;
import game.map.base.BaseMapInfo;
import game.map.base.MapAoiManager;
import game.map.visible.PlayerMapObject;

/**
 * @author : ddv
 * @since : 2019/7/10 下午3:36
 */

public class MainCityMapScene extends AbstractMovableScene<PlayerMapObject> {

    private static final Logger logger = LoggerFactory.getLogger(MainCityMapScene.class);

    public MainCityMapScene(int mapId) {
        super(mapId);
    }

    public static MainCityMapScene valueOf(int mapId, BaseMapInfo baseMapInfo) {
        MainCityMapScene mapScene = new MainCityMapScene(mapId);
        mapScene.baseMapInfo = baseMapInfo;
        mapScene.aoiManager = MapAoiManager.valueOf(baseMapInfo);
        baseMapInfo.setMapScene(mapScene);

        return mapScene;
    }
}
