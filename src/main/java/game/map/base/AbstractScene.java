package game.map.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import game.base.buff.model.BaseCreatureBuff;
import game.map.visible.AbstractVisibleMapObject;
import game.map.visible.impl.MonsterVisibleMapObject;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:20
 */

public abstract class AbstractScene<T extends AbstractVisibleMapObject> {

    /**
     * 地图id
     */
    protected int mapId;

    protected BaseMapInfo baseMapInfo;
    /**
     * buff定时job注册点 key和调度service注册点要保持一致
     */
    private Map<Long, BaseCreatureBuff> buffEffectMap = new HashMap<>();

    public AbstractScene(int mapId) {
        this.mapId = mapId;
    }

    public Map<Long, T> getPlayerMap() {
        return Collections.emptyMap();
    }

    public Map<Long, MonsterVisibleMapObject> getMonsterMap() {
        return Collections.emptyMap();
    }

    /**
     * 获取场景内玩家的同步对象
     *
     * @param playerId
     * @return
     */
    public abstract AbstractVisibleMapObject getPlayerFighter(long playerId);

    // get and set
    public int getMapId() {
        return mapId;
    }

    public BaseMapInfo getBaseMapInfo() {
        return baseMapInfo;
    }

    public Map<Long, BaseCreatureBuff> getBuffEffectMap() {
        return buffEffectMap;
    }
}
