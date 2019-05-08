package game.scene.map.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午12:08
 */

public class CM_EnterMap {

    private long mapId;

    public long getMapId() {
        return mapId;
    }

    public void setMapId(long mapId) {
        this.mapId = mapId;
    }

    @Override
    public String toString() {
        return "CM_EnterMap{" + "mapId=" + mapId + '}';
    }
}
