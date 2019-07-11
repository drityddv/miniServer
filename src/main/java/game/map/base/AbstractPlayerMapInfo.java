package game.map.base;

/**
 * 地图分组为单位的玩家地图记录信息
 *
 * @author : ddv
 * @since : 2019/7/2 下午5:21
 */

public abstract class AbstractPlayerMapInfo {
    /**
     * 上次离线所在的地图id
     */
    private int mapId;

    /**
     * 初始化逻辑
     *
     * @return
     */
    public abstract AbstractPlayerMapInfo valueOf();

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

}
