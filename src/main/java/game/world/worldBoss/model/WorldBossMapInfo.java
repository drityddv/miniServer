package game.world.worldBoss.model;

import java.util.HashMap;
import java.util.Map;

import game.map.base.AbstractMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/2 下午5:39
 */

public class WorldBossMapInfo extends AbstractMapInfo {

    /**
     * 挑战次数 地图id-次数
     */
    private Map<Integer, Integer> challengeRecords = new HashMap<>();

    /**
     * 重置时间
     */
    private long resetTime;

    @Override
    public AbstractMapInfo valueOf() {
        return new WorldBossMapInfo();
    }
}
