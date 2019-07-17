package game.map.base;

import java.util.HashMap;
import java.util.Map;

import game.base.effect.model.BaseBuffEffect;
import game.map.visible.AbstractVisibleMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:20
 */

public abstract class AbstractScene {

    /**
     * 地图id
     */
    protected int mapId;
    /**
     * buff定时job注册点 key和调度service注册点要保持一致
     */
    private Map<Long, BaseBuffEffect> buffEffectMap = new HashMap<>();

    public AbstractScene(int mapId) {
        this.mapId = mapId;
    }

    /**
     * 获取场景内玩家的同步对象
     *
     * @param playerId
     * @return
     */
    public abstract AbstractVisibleMapInfo getPlayerFighter(long playerId);

    // get and set
    public int getMapId() {
        return mapId;
    }

    public Map<Long, BaseBuffEffect> getBuffEffectMap() {
        return buffEffectMap;
    }
}
