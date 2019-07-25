package game.map.visible.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.npc.reource.NpcResource;
import game.map.visible.AbstractMapObject;
import utils.snow.IdUtil;

/**
 * @author : ddv
 * @since : 2019/7/5 下午2:51
 */

public class NpcObject extends AbstractMapObject {

    private static Logger logger = LoggerFactory.getLogger(NpcObject.class);

    private long id;

    private String name;

    private boolean occupying = false;

    public static NpcObject valueOf(NpcResource resource) {
        NpcObject npcVisibleInfo = new NpcObject();
        npcVisibleInfo.id = IdUtil.getLongId();
        npcVisibleInfo.name = resource.getNpcName();
        npcVisibleInfo.init(resource.getX(), resource.getY());
        return npcVisibleInfo;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getAccountId() {
        return "npc";
    }

    public boolean isOccupying() {
        return occupying;
    }

    public void setOccupying(boolean occupying) {
        this.occupying = occupying;
    }

    public String getName() {
        return this.name;
    }
}
