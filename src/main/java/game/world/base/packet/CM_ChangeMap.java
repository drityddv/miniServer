package game.world.base.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午12:20
 */

public class CM_ChangeMap {

    private int mapId;
    private long sceneId;

    public int getMapId() {
        return mapId;
    }

    public long getSceneId() {
        return sceneId;
    }

    @Override
    public String toString() {
        return "CM_ChangeMap{" + "mapId=" + mapId + '}';
    }
}
