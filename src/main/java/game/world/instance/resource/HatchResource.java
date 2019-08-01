package game.world.instance.resource;

import game.world.instance.model.hatch.HatchParam;
import resource.anno.Init;
import resource.anno.MiniResource;

/**
 * @author : ddv
 * @since : 2019/7/31 10:14 AM
 */
@MiniResource
public class HatchResource {
    private long configId;

    private int mapId;
    private int objectId;
    private int num;

    private int bornX;
    private int bornY;

    private HatchParam hatchParam;

    @Init
    private void init() {
        hatchParam = HatchParam.valueOf(this);
    }

    public long getConfigId() {
        return configId;
    }

    public int getMapId() {
        return mapId;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getNum() {
        return num;
    }

    public int getBornX() {
        return bornX;
    }

    public int getBornY() {
        return bornY;
    }

}
