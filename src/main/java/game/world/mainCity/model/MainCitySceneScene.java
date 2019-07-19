package game.world.mainCity.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.base.AbstractMovableScene;
import game.map.visible.PlayerVisibleMapObject;

/**
 * @author : ddv
 * @since : 2019/7/10 下午3:36
 */

public class MainCitySceneScene extends AbstractMovableScene<PlayerVisibleMapObject> {

    private static final Logger logger = LoggerFactory.getLogger(MainCitySceneScene.class);

    public MainCitySceneScene(int mapId) {
        super(mapId);
    }

    public static MainCitySceneScene valueOf(int mapId) {
        return new MainCitySceneScene(mapId);
    }
}
