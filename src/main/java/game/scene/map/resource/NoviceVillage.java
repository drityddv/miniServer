package game.scene.map.resource;

import game.base.map.IMap;
import game.base.map.base.AbstractGameMap;
import game.base.map.base.MapCreature;
import spring.SpringContext;

/**
 * 1:新手村
 *
 * @author : ddv
 * @since : 2019/5/6 下午5:59
 */

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
		map.addCreature(MapCreature.valueOf("传送老头",oldMan,x-1,y-1));
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

		if(creature == null){
			return ;
		}

		double distance = calculateDistance(objectId, oldMan);

		if(distance > 1){
			// 要求距离传送老头1m才可以传送
			return;
		}

		MapCreature mapCreature = getMapCreatures().get(objectId);

		getMapCreatures().remove(objectId);

		mapCreature.reset();

		IMap mapResource = SpringContext.getSceneMapService().getMapResource(2L);

		mapResource.addCreature(mapCreature);
	}

}
