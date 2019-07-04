package game.world.areanMap;

import java.util.HashMap;
import java.util.Map;

import game.world.AbstractMapInfo;

/**
 * 普通地图
 *
 * @author : ddv
 * @since : 2019/7/2 下午10:10
 */

public class ArenaMapInfo extends AbstractMapInfo {
    /**
     * 杀死怪物的数量
     */
    private int killCount;
    /**
     * 上次刷新时间
     */
    private long lastRefreshTime;
    /**
     * 玩家在每个地图的死亡方式
     */
    private Map<Integer, Integer> deadInMap = new HashMap<>();

    @Override
    public AbstractMapInfo valueOf() {
        ArenaMapInfo arenaMapInfo = new ArenaMapInfo();
        arenaMapInfo.setMapId(1);
        return arenaMapInfo;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public long getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(long lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public Map<Integer, Integer> getDeadInMap() {
        return deadInMap;
    }

    public void setDeadInMap(Map<Integer, Integer> deadInMap) {
        this.deadInMap = deadInMap;
    }
}
