package game.map.model;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.visible.AbstractMapObject;
import game.map.visible.impl.NpcObject;

/**
 * @author : ddv
 * @since : 2019/7/19 10:45 AM
 */

public class AoiBroadCastVo {
    private Grid broadCast;
    private String unitName;
    private long id;
    private int x;
    private int y;
    private long hp;
    private long mp;

    public static AoiBroadCastVo valueOf(AbstractMapObject mapObject, int centerX, int centerY) {
        AoiBroadCastVo vo = new AoiBroadCastVo();
        vo.x = mapObject.getCurrentGrid().getX();
        vo.y = mapObject.getCurrentGrid().getY();
        vo.broadCast = Grid.valueOf(centerX, centerY);
        if (mapObject instanceof NpcObject) {
            NpcObject npc = (NpcObject)mapObject;
            vo.id = npc.getId();
            vo.unitName = npc.getName();
            return vo;
        }
        BaseCreatureUnit unit = mapObject.getFighterAccount().getCreatureUnit();
        vo.id = unit.getId();
        vo.unitName = unit.getName();
        vo.hp = unit.getCurrentHp();
        vo.mp = unit.getCurrentMp();

        return vo;
    }

    public String getUnitName() {
        return unitName;
    }

    public long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getHp() {
        return hp;
    }

    public long getMp() {
        return mp;
    }

    public Grid getBroadCast() {
        return broadCast;
    }
}
