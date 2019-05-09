package game.scene.map.resource;

import game.base.map.base.AbstractGameMap;
import game.base.map.base.MapCreature;

/**
 *
 * 2:暴风城
 *
 * @author : ddv
 * @since : 2019/5/7 下午9:44
 */

public class StormWind extends AbstractGameMap {

    public static StormWind valueOf(long mapId, int x, int y) {
        StormWind map = new StormWind();
        int[][] mapData = new int[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                mapData[i][j] = 1;
            }
        }

        map.init(mapId, mapData);

        map.addCreature(MapCreature.valueOf("新手军官", 9L, 1, 1));
        map.addCreature(MapCreature.valueOf("武器商人", 10L, 2, 2));
        map.addCreature(MapCreature.valueOf("护甲商人", 11L, 3, 3));
        return map;
    }

    // 暴风城要求玩家等级满足进入 后续加上
    @Override
    public boolean canEnterMap(String accountId) {
        return true;
    }

    // 暴风城暂时没有传送机制 但是不抛异常
    @Override
    public void transfer(long objectId) {

    }
}
