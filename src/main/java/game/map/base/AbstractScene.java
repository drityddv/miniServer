package game.map.base;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.visible.AbstractMapObject;
import game.map.visible.impl.MonsterMapObject;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:20
 */

public abstract class AbstractScene<T extends AbstractMapObject> {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractScene.class);

    /**
     * 地图id
     */
    protected int mapId;

    protected long sceneId;

    protected BaseMapInfo baseMapInfo;

    public AbstractScene(int mapId) {
        this.mapId = mapId;
    }

    public void init() {

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

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    public BaseMapInfo getBaseMapInfo() {
        return baseMapInfo;
    }

    public void setBaseMapInfo(BaseMapInfo baseMapInfo) {
        this.baseMapInfo = baseMapInfo;
    }

}
