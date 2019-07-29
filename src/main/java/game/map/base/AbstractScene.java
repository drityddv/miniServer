package game.map.base;

import java.util.Collections;
import java.util.Map;

import game.map.visible.AbstractMapObject;
import game.map.visible.impl.MonsterMapObject;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:20
 */

public abstract class AbstractScene<T extends AbstractMapObject> {

    /**
     * 地图id
     */
    protected int mapId;

    protected BaseMapInfo baseMapInfo;

    public AbstractScene(int mapId) {
        this.mapId = mapId;
    }

    public Map<Long, T> getPlayerMap() {
        return Collections.emptyMap();
    }

    public Map<Long, MonsterMapObject> getMonsterMap() {
        return Collections.emptyMap();
    }

    /**
     * 获取场景内玩家的同步对象
     *
     * @param playerId
     * @return
     */
    public abstract AbstractMapObject getPlayerFighter(long playerId);

    // get and set
    public int getMapId() {
        return mapId;
    }

    public BaseMapInfo getBaseMapInfo() {
        return baseMapInfo;
    }

}
