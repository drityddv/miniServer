package game.map.model;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.visible.AbstractVisibleMapObject;

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

    public static AoiBroadCastVo valueOf(AbstractVisibleMapObject mapInfo, int centerX, int centerY) {
        AoiBroadCastVo vo = new AoiBroadCastVo();
        BaseCreatureUnit unit = mapInfo.getFighterAccount().getCreatureUnit();
        vo.id = unit.getId();
        vo.broadCast = Grid.valueOf(centerX, centerY);
        vo.unitName = unit.getName();
        vo.hp = unit.getCurrentHp();
        vo.mp = unit.getCurrentMp();
        vo.x = mapInfo.getCurrentGrid().getX();
        vo.y = mapInfo.getCurrentGrid().getY();
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
