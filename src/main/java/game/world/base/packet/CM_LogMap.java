package game.world.base.packet;

/**
 * 打印地图
 *
 * @author : ddv
 * @since : 2019/7/4 下午4:20
 */

public class CM_LogMap {

    private int mapId;
    private long sceneId;

    public long getSceneId() {
        return sceneId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

}
