package game.world.base.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午12:20
 */

public class CM_ChangeMap {

    private int mapId;

    public int getMapId() {
        return mapId;
    }

    @Override
    public String toString() {
        return "CM_ChangeMap{" + "mapId=" + mapId + '}';
    }
}
