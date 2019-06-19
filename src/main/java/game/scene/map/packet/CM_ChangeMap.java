package game.scene.map.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午12:20
 */

public class CM_ChangeMap {

    private long mapId;

    private long targetMapId;

    public long getMapId() {
        return mapId;
    }

    public void setMapId(long mapId) {
        this.mapId = mapId;
    }

    public long getTargetMapId() {
        return targetMapId;
    }

    public void setTargetMapId(long targetMapId) {
        this.targetMapId = targetMapId;
    }

    @Override
    public String toString() {
        return "CM_ChangeMap{" + "mapId=" + mapId + ", targetMapId=" + targetMapId + '}';
    }
}
