package game.world.mainCity.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.base.AbstractMovableMap;
import game.map.visible.PlayerVisibleMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/10 下午3:36
 */

public class MainCityMapScene extends AbstractMovableMap<PlayerVisibleMapInfo> {

    private static final Logger logger = LoggerFactory.getLogger(MainCityMapScene.class);

    public MainCityMapScene(int mapId) {
        super(mapId);
    }

    public static MainCityMapScene valueOf(int mapId) {
        return new MainCityMapScene(mapId);
    }
}
