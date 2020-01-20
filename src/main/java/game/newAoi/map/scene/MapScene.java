package game.newAoi.map.scene;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.newAoi.map.orthogonal.model.GameAoiCenter;

/**
 * @author : ddv
 * @since : 2020/1/13 3:02 PM
 */
public class MapScene {
    /**
     * 场景所有物体
     */
    private Map<String, MapUnit> mapUnitMap = new HashMap<>();

    private GameAoiCenter aoiCenter;

    private int[][] mapData;

    private int xCapacity;

    private int yCapacity;

    public void init(int x,int y) {
        mapData = new int[x][y];
        xCapacity = mapData.length;
        yCapacity = mapData[0].length;
        aoiCenter = new GameAoiCenter();
        aoiCenter.init(this);
    }

    public void enterScene(MapUnit unit) {
        if (mapUnitMap.containsKey(unit.getUnitName())) {
            return;
        }
        mapUnitMap.put(unit.getUnitName(), unit);
        triggerAoi(unit);

    }

    private void triggerAoi(MapUnit unit) {
        aoiCenter.handleJoinScene(unit);
        broadcastJoin(unit);
    }

    /**
     * 广播
     */
    public void broadcastJoin(MapUnit unit) {

    }

    /**
     * 获取中心点
     */
    public Coordinate getCenterCoordinate() {
        Coordinate coordinate = new Coordinate();
        // int x = mapData.length >> 1;
        // int y = mapData[0].length >> 1;
        coordinate.setX(0);
        coordinate.setY(0);
        return coordinate;
    }

    public List<MapUnit> getVisibleList(String unitName) {
        if (!mapUnitMap.containsKey(unitName)) {
            return Collections.emptyList();
        }
        return aoiCenter.getVisibleList(mapUnitMap.get(unitName));
    }

    public void printMap() {
        List<Coordinate> coordinateList = aoiCenter.getAllCoordinateList();
        int[][] printData = new int[xCapacity][yCapacity];
        coordinateList.forEach(coordinate -> printData[coordinate.getX()][coordinate.getY()] = 1);

        for (int[] datum : printData) {
            Utils.logArray(datum);
        }

    }

    public GameAoiCenter getAoiCenter() {
        return aoiCenter;
    }

	public int[][] getMapData() {
		return mapData;
	}
}
