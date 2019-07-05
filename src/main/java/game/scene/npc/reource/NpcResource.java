package game.scene.npc.reource;

import middleware.anno.Init;
import middleware.anno.MiniResource;

/**
 * @author : ddv
 * @since : 2019/7/5 上午11:35
 */
@MiniResource
public class NpcResource {
    private int configId;
    private String npcName;
    private int x;
    private int y;
    private int mapId;

    private String functionString;
    private String rewardString;

    @Init
    public void init() {

    }

    public int getId() {
        return configId;
    }

    public void setId(int id) {
        this.configId = id;
    }

    public String getNpcName() {
        return npcName;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
