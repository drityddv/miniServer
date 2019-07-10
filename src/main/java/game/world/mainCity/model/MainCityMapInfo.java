package game.world.mainCity.model;

import game.map.base.AbstractMapInfo;

/**
 * 主城
 *
 * @author : ddv
 * @since : 2019/7/10 下午3:32
 */

public class MainCityMapInfo extends AbstractMapInfo {

    @Override
    public AbstractMapInfo valueOf() {
        return new MainCityMapInfo();
    }
}
