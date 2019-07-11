package game.map.base;

import java.util.HashMap;
import java.util.Map;

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
     * 定时器容器
     */
    private Map<Object, Object> commandMap = new HashMap<>();

    public AbstractScene(int mapId) {
        this.mapId = mapId;
    }

    /**
     * 获取场景内玩家的同步对象
     *
     * @return
     */
    public abstract AbstractVisibleMapInfo getPlayerFighter(String accountId);

    // get and set
    public int getMapId() {
        return mapId;
    }

    public Map<Object, Object> getCommandMap() {
        return commandMap;
    }

}
