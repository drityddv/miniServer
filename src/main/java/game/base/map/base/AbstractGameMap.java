package game.base.map.base;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.map.IMap;
import game.common.Ii8n;
import game.common.exception.RequestException;

/**
 * @author : ddv
 * @since : 2019/5/7 上午9:47
 */

public abstract class AbstractGameMap implements IMap {

    private static final Logger logger = LoggerFactory.getLogger(AbstractGameMap.class);

    private long mapId;
    private int x;
    private int y;
    private int[][] mapData;
    private ConcurrentHashMap<Long, MapCreature> mapCreatures;

    public void init(long mapId, int[][] mapData) {
        this.mapId = mapId;
        this.x = mapData.length;
        this.y = mapData[0].length;
        this.mapData = mapData;
        mapCreatures = new ConcurrentHashMap<>(16);
    }

    @Override
    public long getCurrentMapId() {
        return mapId;
    }

    @Override
    public void printMap() {
        logger.info("打印地图[{}]详细信息,地图长[{}],宽[{}]", this.getMapId(), this.getX(), this.getY());
        logger.info("地图详细信息");
        printMapData();
        logger.info("地图生物单位数量[{}]", mapCreatures.size());
        mapCreatures.forEach((objectId, mapCreature) -> {
            mapCreature.print();
        });

    }

    @Override
    public void move(long objectId, int targetX, int targetY) {
        MapCreature creature = getCreature(objectId);

        if (creature == null) {
            logger.warn("角色[{}]不存在地图[{}],无法移动!", objectId, mapId);
            RequestException.throwException(Ii8n.MAP_CREATURE_NOT_EXIST);
        }

        if (!checkTarget(targetX, targetY)) {
            logger.warn("玩家移动坐标有误,坐标[{},{}]", targetX, targetY);
            RequestException.throwException(Ii8n.TARGET_POSITION_ERROR);
        }

        creature.setX(targetX);
        creature.setY(targetY);

    }

    @Override
    public MapCreature getCreature(long objectId) {
        return mapCreatures.get(objectId);
    }

    @Override
    public void addCreature(MapCreature creature) {
        long id = creature.getId();
        if (!mapCreatures.contains(id)) {
            mapCreatures.put(id, creature);
        }
    }

    @Override
    public void deleteCreature(long objectId) {
        mapCreatures.remove(objectId);
    }

    @Override
    public double calculateDistance(long objectId, long targetObjectId) {
        MapCreature from = mapCreatures.get(objectId);
        MapCreature target = mapCreatures.get(targetObjectId);

        if (from == null || target == null) {
            RequestException.throwException(Ii8n.MAP_CREATURE_NOT_EXIST);
        }

        double distanceX = Math.pow((from.getX() - target.getX()), 2);
        double distanceY = Math.pow((from.getY() - target.getY()), 2);
        return Math.sqrt(distanceX + distanceY);

    }

    // get and set

    public long getMapId() {
        return mapId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public ConcurrentHashMap<Long, MapCreature> getMapCreatures() {
        return mapCreatures;
    }

    private void printMapData() {
        int[][] mapData = this.getMapData();
        int x = this.getX();
        int y = this.getY();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(mapData[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean checkTarget(int targetX, int targetY) {

        if (targetX < 0 || targetY < 0 || targetX > x - 1 || targetY > y - 1 || mapData[targetX][targetY] == 0) {
            return false;
        }

        return true;
    }

}
