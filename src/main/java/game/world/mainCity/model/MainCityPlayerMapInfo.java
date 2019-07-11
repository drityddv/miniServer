package game.world.mainCity.model;

import game.map.base.AbstractPlayerMapInfo;

/**
 * 主城
 *
 * @author : ddv
 * @since : 2019/7/10 下午3:32
 */

public class MainCityPlayerMapInfo extends AbstractPlayerMapInfo {

    @Override
    public AbstractPlayerMapInfo valueOf() {
        return new MainCityPlayerMapInfo();
    }
}
