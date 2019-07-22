package game.map.base;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.model.Grid;
import game.map.visible.AbstractVisibleMapObject;
import game.map.visible.impl.MonsterVisibleMapObject;
import game.world.base.resource.MapBlockResource;
import game.world.utils.MapUtil;

/**
 * @author : ddv
 * @since : 2019/7/18 11:10 PM
 */
public class MapAoiManager {

    private final Logger logger = LoggerFactory.getLogger(MapAoiManager.class);
    private BaseMapInfo mapInfo;
    private Map<Grid, List<BroadcastCenter>> centerMap;

    public static MapAoiManager valueOf(BaseMapInfo mapInfo) {
        MapAoiManager aoiManager = new MapAoiManager();
        aoiManager.mapInfo = mapInfo;
        aoiManager.centerMap = mapInfo.getBlockResource().getAoiModelMap();
        aoiManager.centerMap.forEach((grid, aoiCenters) -> {
            aoiCenters.forEach(aoiCenter -> aoiCenter.init(grid));
        });
        return aoiManager;
    }

    // 玩家进入触发广播
    public <T extends AbstractVisibleMapObject> void triggerEnter(T object) {
        Grid currentGrid = object.getCurrentGrid();
        List<BroadcastCenter> broadcastCenters = centerMap.get(currentGrid);
        if (broadcastCenters == null) {
            logger.warn("单位[{}] 坐标[{} {}] 无广播中心", object.getId(), currentGrid.getX(), currentGrid.getY());
            return;
        }
        broadcastCenters.forEach(aoiCenter -> aoiCenter.triggerEnter(object));
    }

    public <T extends AbstractVisibleMapObject> void triggerMove(T object, Grid targetGrid) {
        List<BroadcastCenter> originCenters = centerMap.get(object.getCurrentGrid());
        List<BroadcastCenter> targetCenters = centerMap.get(targetGrid);

        object.getTargetGrid().setX(targetGrid.getX());
        object.getTargetGrid().setY(targetGrid.getY());
        MapBlockResource blockResource = mapInfo.getBlockResource();

        if (MapUtil.doMove(object, blockResource.getBlockData())) {
            // 失去玩家焦点的旧广播中心 移除状态并且广播
            originCenters = originCenters.stream().filter(broadcastCenter -> !isExist(broadcastCenter, targetCenters))
                .collect(Collectors.toList());
            originCenters.forEach(broadcastCenter -> {
                broadcastCenter.removeUnit(object, 1);
            });

            // 持有玩家视野注册[新广播]状态并广播
            targetCenters.forEach(broadcastCenter -> broadcastCenter.registerUnit(object));
        }

    }

    // 玩家离开地图触发广播
    public <T extends AbstractVisibleMapObject> void triggerLeave(T object) {
        Grid currentGrid = object.getCurrentGrid();
        List<BroadcastCenter> broadcastCenters = centerMap.get(currentGrid);
        broadcastCenters.forEach(broadcastCenter -> {
            broadcastCenter.removeUnit(object, 2);
        });
    }

    public void registerUnits(MonsterVisibleMapObject monsterVisibleMapInfo) {
        Grid grid = monsterVisibleMapInfo.getCurrentGrid();
        List<BroadcastCenter> broadcastCenters = centerMap.get(grid);
        broadcastCenters.forEach(aoiCenter -> {
            aoiCenter.registerUnit(monsterVisibleMapInfo);
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
}
