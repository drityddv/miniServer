package game.world.base.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午12:20
 */

public class CM_ChangeMap {

    private int mapId;
    private long sceneId;

    public static CM_ChangeMap valueOf(int mapId, long sceneId) {
        CM_ChangeMap cm = new CM_ChangeMap();
        cm.setMapId(mapId);
        cm.setSceneId(sceneId);
        return cm;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public String toString() {
        return "CM_ChangeMap{" + "mapId=" + mapId + '}';
    }
}
