package game.map.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.map.model.Grid;
import game.map.packet.SM_AoiBroadCast;
import game.map.visible.AbstractVisibleMapObject;
import game.map.visible.PlayerVisibleMapObject;
import net.utils.PacketUtil;

/**
 * 广播中心
 *
 * @author : ddv
 * @since : 2019/7/18 9:51 PM
 */

public class BroadcastCenter {
    // 广播中心坐标
    private int pointX;
    private int pointY;
    // 广播坐标集合
    private List<Grid> scopeGrid = new ArrayList<>();
    // 广播范围内的单位
    private Map<Grid, Map<Long, AbstractVisibleMapObject>> unitMap = new HashMap<>();

    public BroadcastCenter(int pointX, int pointY, List<Grid> scopeGrid) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.scopeGrid = scopeGrid;
    }

    public BroadcastCenter(int pointX, int pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public static BroadcastCenter valueOf(int pointX, int pointY, List<Grid> scopeGrid) {
        BroadcastCenter aoiModel = new BroadcastCenter(pointX, pointY, scopeGrid);
        scopeGrid.forEach(grid -> {
            aoiModel.unitMap.put(grid, new HashMap<>());
        });
        return aoiModel;
    }

    // 广播信息
    public void broadCastUnits() {
        List<AbstractVisibleMapObject> objects = new ArrayList<>();
        unitMap.values().forEach(girdUnitMap -> objects.addAll(girdUnitMap.values()));

        SM_AoiBroadCast sm = SM_AoiBroadCast.valueOf(objects);

        objects.stream().filter(mapInfo -> mapInfo instanceof PlayerVisibleMapObject).forEach(mapInfo -> {
            PlayerVisibleMapObject playerVisibleMapInfo = (PlayerVisibleMapObject)mapInfo;
            PacketUtil.send(playerVisibleMapInfo.getPlayer(), sm);
        });
    }

    public void triggerEnter(AbstractVisibleMapObject object) {
        Grid currentGrid = object.getCurrentGrid();
        Map<Long, AbstractVisibleMapObject> mapInfoMap = unitMap.get(currentGrid);
        mapInfoMap.put(object.getId(), object);
        broadCastUnits();
    }

    public void init(Grid grid) {
        unitMap.put(grid, new HashMap<>());
    }

    // 清除单位并且广播
    public <T extends AbstractVisibleMapObject> void removeUnit(T object) {
        Grid lastGrid = object.getLastGrid();
        unitMap.get(lastGrid).remove(object.getId());
        broadCastUnits();
    }

    // 注册单位并且广播
    public void registerUnit(AbstractVisibleMapObject mapInfo) {
        unitMap.get(mapInfo.getCurrentGrid()).put(mapInfo.getId(), mapInfo);
        broadCastUnits();
    }

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    public List<Grid> getScopeGrid() {
        return scopeGrid;
    }

    public void setScopeGrid(List<Grid> scopeGrid) {
        this.scopeGrid = scopeGrid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BroadcastCenter that = (BroadcastCenter)o;

        if (pointX != that.pointX) {
            return false;
        }
        return pointY == that.pointY;
    }

    @Override
    public int hashCode() {
        int result = pointX;
        result = 31 * result + pointY;
        return result;
    }

}
