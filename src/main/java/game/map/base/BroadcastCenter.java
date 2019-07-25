package game.map.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.model.Grid;
import game.map.packet.SM_AoiBroadCast;
import game.map.visible.AbstractMapObject;
import game.map.visible.BaseAttackAbleMapObject;
import game.map.visible.PlayerMapObject;
import game.world.base.constant.Map_Constant;
import game.world.utils.MapUtil;
import net.utils.PacketUtil;
import utils.CollectionUtil;

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
    // 广播范围内的单位
    private Map<Grid, Map<Long, AbstractMapObject>> unitMap = new HashMap<>();

    public BroadcastCenter(int pointX, int pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public static BroadcastCenter valueOf(BroadcastCenter broadcastCenter) {
        BroadcastCenter copy = new BroadcastCenter(broadcastCenter.pointX, broadcastCenter.pointY);
        broadcastCenter.unitMap.forEach((grid, longAbstractVisibleMapObjectMap) -> {
            copy.unitMap.put(Grid.valueOf(grid), new HashMap<>());
        });
        return copy;
    }

    public void broadCastUnits(AbstractMapObject object) {
        if (!(object instanceof PlayerMapObject)) {
            return;
        }

        List<AbstractMapObject> objects = new ArrayList<>();
        unitMap.values().forEach(girdUnitMap -> objects.addAll(girdUnitMap.values()));

        List<AbstractMapObject> smObjects =
            objects.stream().filter(mapObject -> MapUtil.calculateDistance(object.getCurrentGrid(),
                mapObject.getCurrentGrid()) <= Map_Constant.VISIBLE_DISTANCE).collect(Collectors.toList());

        SM_AoiBroadCast sm = SM_AoiBroadCast.valueOf(smObjects, pointX, pointY);
        PlayerMapObject playerVisibleMapInfo = (PlayerMapObject)object;
        PacketUtil.send(playerVisibleMapInfo.getPlayer(), sm);

    }

    public void broadCastAllUnits() {
        List<AbstractMapObject> objects = new ArrayList<>();
        unitMap.values().forEach(girdUnitMap -> objects.addAll(girdUnitMap.values()));
        objects.forEach(abstractVisibleMapObject -> {
            broadCastUnits(abstractVisibleMapObject);
        });
    }

    public void triggerEnter(AbstractMapObject object) {
        Grid lastGrid = object.getLastGrid();
        if (unitMap.get(lastGrid).containsKey(object.getId())) {
            unitMap.get(lastGrid).remove(object.getId());
        }
        Grid currentGrid = object.getCurrentGrid();
        Map<Long, AbstractMapObject> mapInfoMap = unitMap.get(currentGrid);
        mapInfoMap.put(object.getId(), object);
        broadCastUnits(object);
    }

    public void init(Grid grid) {
        unitMap.put(grid, new HashMap<>());
    }

    // 清除单位并且广播 type==1 移动 type==2 离开
    public <T extends AbstractMapObject> void removeUnit(T object, int type) {
        Grid grid = object.getLastGrid();
        if (type == 2) {
            grid = object.getCurrentGrid();
        }
        unitMap.get(grid).remove(object.getId());
        broadCastAllUnits();
    }

    public List<BaseCreatureUnit> getCreatureUnitsByGrid(Grid grid) {
        List<BaseCreatureUnit> creatureUnits = new ArrayList<>();
        Map<Long, AbstractMapObject> mapObjectMap = unitMap.get(grid);
        mapObjectMap.values().forEach(mapObject -> {
            if (mapObject instanceof BaseAttackAbleMapObject) {
                creatureUnits.add(mapObject.getFighterAccount().getCreatureUnit());
            }
        });

        return creatureUnits;
    }

    // 注册单位并且广播
    public void registerUnit(AbstractMapObject object) {
        Map<Long, AbstractMapObject> lastGrid = unitMap.get(object.getLastGrid());

        if (lastGrid != null && lastGrid.containsKey(object.getId())) {
            lastGrid.remove(object.getId());
        }
        unitMap.get(object.getCurrentGrid()).put(object.getId(), object);
        broadCastUnits(object);
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

    public List<AbstractMapObject> getAreaObjects() {
        List<AbstractMapObject> objects = CollectionUtil.emptyArrayList();
        unitMap.values().forEach(objectMap -> {
            objects.addAll(objectMap.values());
        });

        return objects;
    }

    public Map<Grid, Map<Long, AbstractMapObject>> getUnitMap() {
        return unitMap;
    }

    @Override
    public int hashCode() {
        int result = pointX;
        result = 31 * result + pointY;
        return result;
    }

}
