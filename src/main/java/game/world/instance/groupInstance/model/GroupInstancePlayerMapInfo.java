package game.world.instance.groupInstance.model;

import game.map.base.AbstractPlayerMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/30 5:54 PM
 */

public class GroupInstancePlayerMapInfo extends AbstractPlayerMapInfo {

    @Override
    public AbstractPlayerMapInfo valueOf() {
        return new GroupInstancePlayerMapInfo();
    }
}
