package game.scene.map.resource;

import java.io.InputStream;

import game.base.map.IMap;
import game.base.map.base.AbstractGameMap;
import game.base.map.base.MapCreature;
import game.common.Ii8n;
import game.common.exception.RequestException;
import middleware.anno.MapResource;
import spring.SpringContext;

/**
 * 1:新手村
 *
 * @author : ddv
 * @since : 2019/5/6 下午5:59
 */
@MapResource()
public class NoviceVillage extends AbstractGameMap {

    private static long oldMan = 8L;

    public static NoviceVillage valueOf(long mapId, int x, int y) {
        NoviceVillage map = new NoviceVillage();

        int[][] mapData = new int[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                mapData[i][j] = 1;
            }
        }

        map.init(mapId, mapData);
        // 这里先写死一个npc 用来传送到暴风城
        map.addCreature(MapCreature.valueOf("传送老头", oldMan, x - 1, y - 1));
        return map;
    }

    // 新手村不做进入限制
    @Override
    public boolean canEnterMap(String accountId) {
        return true;
    }

    @Override
    public void transfer(long objectId) {
        MapCreature creature = getCreature(objectId);

        if (creature == null) {
            RequestException.throwException(Ii8n.MAP_CREATURE_NOT_EXIST);
            return;
        }

        double distance = calculateDistance(objectId, oldMan);

        if (distance > 1) {
            // 要求直线距离传送老头最远1m才可以传送
            RequestException.throwException(Ii8n.DISTANCE_TOO_FAR);
            return;
        }

        MapCreature mapCreature = getMapCreatures().get(objectId);
        getMapCreatures().remove(objectId);
        mapCreature.reset();

        IMap mapResource = SpringContext.getSceneMapService().getMapResource(2L);
        mapResource.addCreature(mapCreature);

        SpringContext.getSceneMapService().modifyPlayerMapStatus(objectId, 2L);
    }

    @Override
    public IMap initFromInputStream(InputStream inputStream, int index) {
        NoviceVillage noviceVillage = new NoviceVillage();
        noviceVillage.init(inputStream, index);
        noviceVillage.addCreature(MapCreature.valueOf("传送老头", oldMan, 4, 4));
        return noviceVillage;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
