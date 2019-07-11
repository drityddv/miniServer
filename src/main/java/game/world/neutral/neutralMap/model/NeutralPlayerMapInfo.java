package game.world.neutral.neutralMap.model;

import game.map.base.AbstractPlayerMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:14
 */

public class NeutralPlayerMapInfo extends AbstractPlayerMapInfo {

    @Override
    public NeutralPlayerMapInfo valueOf() {
        NeutralPlayerMapInfo mapInfo = new NeutralPlayerMapInfo();
        return mapInfo;
    }
}
