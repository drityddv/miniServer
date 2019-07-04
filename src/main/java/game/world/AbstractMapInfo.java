package game.world;

/**
 * @author : ddv
 * @since : 2019/7/2 下午5:21
 */

public abstract class AbstractMapInfo {
    /**
     * 上次离线所在的地图id
     */
    private int mapId;

    /**
     * 清除信息
     */
    public void clear() {

    }

    /**
     * 初始化逻辑
     *
     * @return
     */
    public abstract AbstractMapInfo valueOf();

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

}
