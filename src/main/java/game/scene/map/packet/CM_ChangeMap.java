package game.scene.map.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午12:20
 */

public class CM_ChangeMap {

    private long fromMapId;

    private long targetMapId;

    public long getFromMapId() {
        return fromMapId;
    }

    public void setFromMapId(long fromMapId) {
        this.fromMapId = fromMapId;
    }

    public long getTargetMapId() {
        return targetMapId;
    }

    public void setTargetMapId(long targetMapId) {
        this.targetMapId = targetMapId;
    }

    @Override
    public String toString() {
        return "CM_ChangeMap{" + "fromMapId=" + fromMapId + ", targetMapId=" + targetMapId + '}';
    }
}
