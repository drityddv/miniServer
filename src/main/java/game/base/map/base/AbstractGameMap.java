package game.base.map.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.map.IMap;

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

	public void init(long mapId, int[][] mapData) {
		this.mapId = mapId;
		this.x = mapData.length;
		this.y = mapData[0].length;
		this.mapData = mapData;
	}

	public boolean checkTarget(int targetX, int targetY) {

		if (targetX < 0 || targetY < 0 || targetX > x - 1 || targetY > y - 1 || mapData[x][y] == 0) {
			return false;
		}

		return true;
	}

	@Override
	public long getCurrentMapId() {
		return getMapId();
	}

	@Override
	public void printMap() {
		basicLogMap(logger);
	}

	//abstract method

	/***
	 * 打印地图信息
	 */
	public abstract void basicLogMap(Logger logger);

	//get and set

	public long getMapId() {
		return mapId;
	}

	public void setMapId(long mapId) {
		this.mapId = mapId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int[][] getMapData() {
		return mapData;
	}

	public void setMapData(int[][] mapData) {
		this.mapData = mapData;
	}
}
