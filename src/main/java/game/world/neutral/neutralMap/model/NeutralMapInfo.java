package game.world.neutral.neutralMap.model;

import game.miniMap.base.AbstractMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:14
 */

public class NeutralMapInfo extends AbstractMapInfo {

    @Override
    public NeutralMapInfo valueOf() {
        NeutralMapInfo mapInfo = new NeutralMapInfo();
        return mapInfo;
    }
}
