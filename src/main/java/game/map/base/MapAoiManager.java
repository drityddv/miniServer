package game.map.base;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.model.Grid;
import game.map.visible.AbstractMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.world.base.resource.MapBlockResource;
import game.world.utils.MapUtil;
import utils.CollectionUtil;

/**
 * aoi模块
 *
 * @author : ddv
 * @since : 2019/7/18 11:10 PM
 */
public class MapAoiManager {

    private final Logger logger = LoggerFactory.getLogger(MapAoiManager.class);
    private BaseMapInfo mapInfo;
    // 1 1 2 2

    private Map<Grid, BroadcastCenter> centerMap;

    public static MapAoiManager valueOf(BaseMapInfo mapInfo) {
        MapAoiManager aoiManager = new MapAoiManager();
        aoiManager.mapInfo = mapInfo;
        aoiManager.centerMap = aoiManager.copyAoi(mapInfo.getBlockResource().getAoiModelMap());
        return aoiManager;
    }

    // 玩家进入触发广播
    public <T extends AbstractMapObject> void triggerEnter(T object) {
        Grid currentGrid = object.getCurrentGrid();
        BroadcastCenter broadcastCenters = centerMap.get(currentGrid);
        if (broadcastCenters == null) {
            logger.warn("单位[{}] 坐标[{} {}] 无广播中心", object.getId(), currentGrid.getX(), currentGrid.getY());
            return;
        }
        broadcastCenters.triggerEnter(object);
    }

    public <T extends AbstractMapObject> void triggerMove(T object, Grid targetGrid) {
        BroadcastCenter originCenter = centerMap.get(object.getCurrentGrid());
        BroadcastCenter targetCenter = centerMap.get(targetGrid);

        object.getTargetGrid().setX(targetGrid.getX());
        object.getTargetGrid().setY(targetGrid.getY());
        MapBlockResource blockResource = mapInfo.getBlockResource();

        if (MapUtil.doMove(object, blockResource.getBlockData())) {
            // 失去玩家焦点的旧广播中心 移除状态并且广播
            originCenter.removeUnit(object, 1);
            // 持有玩家视野注册[新广播]状态并广播
            targetCenter.registerUnit(object);
        }

    }

    // 玩家离开地图触发广播
    public <T extends AbstractMapObject> void triggerLeave(T object) {
        Grid currentGrid = object.getCurrentGrid();
        BroadcastCenter broadcastCenter = centerMap.get(currentGrid);
        broadcastCenter.removeUnit(object, 2);

    }

    public void removeMonsters() {
        centerMap.values().forEach(broadcastCenter -> {
            Collection<Map<Long, AbstractMapObject>> mapObjectsCollection = broadcastCenter.getUnitMap().values();
            mapObjectsCollection.forEach(objectMap -> {
                Map<Long, AbstractMapObject> temp = new HashMap<>(objectMap);
                temp.values().forEach(mapObject -> {
                    if (mapObject instanceof MonsterMapObject) {
                        objectMap.remove(mapObject.getId());
                    }
                });
            });
        });
    }

    private boolean isExist(BroadcastCenter temp, List<BroadcastCenter> centers) {
        for (BroadcastCenter center : centers) {
            if (center.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    public List<AbstractMapObject> getAreaObjects(Grid grid) {
        List<AbstractMapObject> creatureUnits = CollectionUtil.emptyArrayList();
        BroadcastCenter broadcastCenter = centerMap.get(grid);
        creatureUnits.addAll(broadcastCenter.getAreaObjects());
        return creatureUnits;
    }

    public List<BaseCreatureUnit> getCreatureUnits(List<Grid> gridList) {
        List<BaseCreatureUnit> creatureUnits = new ArrayList<>();
        gridList.forEach(grid -> {
            BroadcastCenter broadcastCenter = centerMap.get(grid);
            creatureUnits.addAll(broadcastCenter.getCreatureUnitsByGrid(grid));
        });
        return creatureUnits.stream().distinct().collect(Collectors.toList());
    }

    private Map<Grid, BroadcastCenter> copyAoi(Map<Grid, BroadcastCenter> aoiModelMap) {
        Map<Grid, BroadcastCenter> copy = new HashMap<>();
        aoiModelMap.forEach((grid, broadcastCenter) -> {
            BroadcastCenter center = BroadcastCenter.valueOf(broadcastCenter);
            center.getUnitMap().keySet().forEach(grid1 -> {
                copy.put(grid1, center);
            });
        });
        return copy;
    }

    public void registerUnits(MonsterMapObject monsterVisibleMapInfo) {
        Grid grid = monsterVisibleMapInfo.getCurrentGrid();
        BroadcastCenter broadcastCenter = centerMap.get(grid);
        broadcastCenter.registerUnit(monsterVisibleMapInfo);
    }

    public void registerUnits(AbstractMapObject object) {
        Grid grid = object.getCurrentGrid();
        BroadcastCenter broadcastCenter = centerMap.get(grid);
        broadcastCenter.registerUnit(object);
    }

    public void broadcast(Grid grid) {
        centerMap.get(grid).broadCastAllUnits();
    }
}
