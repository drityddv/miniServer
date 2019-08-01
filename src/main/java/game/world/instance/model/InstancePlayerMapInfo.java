package game.world.instance.model;

import game.map.base.AbstractPlayerMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/30 5:54 PM
 */

public class InstancePlayerMapInfo extends AbstractPlayerMapInfo {
    @Override
    public AbstractPlayerMapInfo valueOf() {
        return new InstancePlayerMapInfo();
    }
}
