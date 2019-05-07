package game.scene.map.resource;

import game.base.map.base.AbstractGameMap;
import game.base.map.base.MapCreature;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 新手村
 *
 * @author : ddv
 * @since : 2019/5/6 下午5:59
 */

public class NoviceVillage extends AbstractGameMap {

	private ConcurrentHashMap<Long, MapCreature> mapCreatures = new ConcurrentHashMap<>();

	public static NoviceVillage valueOf(long mapId, int x, int y) {
		NoviceVillage map = new NoviceVillage();

		int[][] mapData = new int[x][y];

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				mapData[i][j] = 1;
			}
		}

		map.init(mapId, mapData);
		return map;
	}

	@Override
	public void move(long objectId, int x, int y) {
		MapCreature creature = mapCreatures.get(objectId);

		if (creature == null) {
			return;
		}

		if (!checkTarget(x, y)) {
			return;
		}

		creature.setX(x);
		creature.setY(y);
	}

	@Override
	public void basicLogMap(Logger logger) {
		logger.info("打印地图[{}]详细信息,地图长[{}],宽[{}]", this.getMapId(), this.getX(), this.getY());
		logger.info("地图详细信息");
		printMapData();
		logger.info("地图生物单位数量[{}]", mapCreatures.size());
	}

	// 新手村不做进入限制
	@Override
	public boolean canEnterMap(String accountId) {
		return true;
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
}
