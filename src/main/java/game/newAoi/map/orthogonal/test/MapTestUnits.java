package game.newAoi.map.orthogonal.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.newAoi.map.orthogonal.model.GameAoiCenter;
import game.newAoi.map.orthogonal.model.OrthogonalNode;
import game.newAoi.map.scene.Coordinate;
import game.newAoi.map.scene.MapScene;
import game.newAoi.map.scene.MapUnit;
import game.newAoi.map.scene.Utils;

/**
 * @author : ddv
 * @since : 2020/1/15 11:47 AM
 */
public class MapTestUnits {

    private static final Logger logger = LoggerFactory.getLogger(MapTestUnits.class);

    public static void testEnterMap(int x, int y, int unitNum, int testTimes) {
        if (x <= 0 || y <= 0 || unitNum > x * y) {
            logger.warn("参数有误[{} {} {}]", x, y, unitNum);
            return;
        }
        while (testTimes > 0) {
            MapScene mapScene = new MapScene();
            mapScene.init(x, y);
            List<Coordinate> coordinateSet = new ArrayList<>();
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    coordinateSet.add(Coordinate.valueOf(i, j));
                }
            }

            for (int i = 0; i < unitNum; i++) {
                Coordinate coordinate = Utils.getRandomElementFromList(coordinateSet);
                MapUnit mapUnit = MapUnit.valueOf("unit_" + coordinate.getX() + "_" + coordinate.getY(),
                    coordinate.getX(), coordinate.getY());
                mapScene.enterScene(mapUnit);
            }
            if (!testMap(mapScene)) {
                return;
            }
            testTimes--;
        }
    }

    public static boolean testMap(MapScene mapScene) {
        OrthogonalNode node = mapScene.getAoiCenter().getRowHead();
        List<OrthogonalNode> templateNodeOrder = mapUnitsOrder(mapScene);

        for (int i = 0; i < templateNodeOrder.size(); i++) {
            if (node == null || node != templateNodeOrder.get(i)) {
                logger.warn("链表顺序[{} {}]不匹配", templateNodeOrder.get(i).getX(), templateNodeOrder.get(i).getY());
                mapScene.printMap();
                return false;
            }

            node = node.getRowNextNode();
            if (i + 1 == templateNodeOrder.size()) {
                if (node != null) {
                    logger.warn("链表顺序[{} {}]不匹配", node.getX(), node.getY());
                    mapScene.printMap();
                    return false;
                }
            }
        }

        logger.info("地图aoi测试通过...");
        return true;

    }

    /**
     * 返回地图中理论上正确的链表结构
     */
    public static List<OrthogonalNode> mapUnitsOrder(MapScene mapScene) {
        List<OrthogonalNode> node = new ArrayList<>();
        GameAoiCenter aoiCenter = mapScene.getAoiCenter();
        Collection<MapUnit> mapUnits = aoiCenter.getUnitMap().values();
        int[][] mapData = mapScene.getMapData();
        node.add(aoiCenter.getRowHead());
        for (int y = 0; y < mapData[0].length; y++) {
            for (int x = 0; x < mapData.length; x++) {
                MapUnit unit = findUnit(x, y, mapUnits);
                if (unit != null) {
                    node.add(unit.getxNode());
                }
            }
        }

        return node;
    }

    private static MapUnit findUnit(int x, int y, Collection<MapUnit> units) {
        for (MapUnit mapUnit : units) {
            if (mapUnit.getCoordinate().getX() == x && mapUnit.getCoordinate().getY() == y) {
                return mapUnit;
            }
        }
        return null;
    }
}
