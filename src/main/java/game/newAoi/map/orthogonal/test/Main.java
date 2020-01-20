package game.newAoi.map.orthogonal.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2020/1/13 10:09 AM
 */

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        // MapScene mapScene = new MapScene();
        // mapScene.init(5,10);
        //
        // MapUnit unit_3_3 = MapUnit.valueOf("3_3", 3, 3);
        // MapUnit unit_2_1 = MapUnit.valueOf("2_1", 2, 1);
        // MapUnit unit_3_1 = MapUnit.valueOf("3_1", 3, 1);
        // MapUnit unit_2_0 = MapUnit.valueOf("2_0", 2, 0);
        // MapUnit unit_1_0 = MapUnit.valueOf("1_0", 1, 0);
        // MapUnit unit_0_1 = MapUnit.valueOf("0_1", 0, 1);
        //
        // mapScene.enterScene(unit_3_3);
        // mapScene.enterScene(unit_2_1);
        // mapScene.enterScene(unit_3_1);
        // mapScene.enterScene(unit_2_0);
        // mapScene.enterScene(unit_1_0);
        // mapScene.enterScene(unit_0_1);
        //
        // mapScene.printMap();
        //
        // // visibleList = mapScene.getVisibleList(unit_3_3.getUnitName());
        // // for (MapUnit mapUnit : visibleList) {
        // // logger.info("地图单位[{}] 坐标信息[{} {}]", mapUnit.getUnitName(), mapUnit.getCoordinate().getX(),
        // // mapUnit.getCoordinate().getY());
        // // }
        //
        // // OrthogonalNode node = mapScene.getAoiCenter().getRowHead();
        // // while (node != null) {
        // // logger.info("节点[{} {} {}]", node.getUnit().getUnitName(), node.getX(), node.getY());
        // // node = node.getRowNextNode();
        // // }
        //
        // // Scanner scanner = new Scanner(System.in);
        // // while (scanner.hasNext()) {
        // // String input = scanner.nextLine();
        // // String[] split = input.split(" ");
        // // if (split.length == 3) {
        // // mapScene.enterScene(MapUnit.valueOf(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        // // mapScene.printMap();
        // // }
        // // }
        // MapTestUnits.testMap(mapScene);
        // Thread.sleep(100);

         MapTestUnits.testEnterMap(10000,10000,50,2);

    }

    private static void changeMap(Map map) {
        map = new HashMap();
        logger.info("[{}]", map.hashCode());
    }

    @Test
    public void test() {

        test1();

    }

    private void test1() {
        Map<Integer, Integer> map = new HashMap<>();
		map.putIfAbsent(1,1);
//        map.put(0, 1);
		Integer orDefault = map.getOrDefault(1, map.getOrDefault(0, null));
		System.out.println(orDefault);
    }
}
