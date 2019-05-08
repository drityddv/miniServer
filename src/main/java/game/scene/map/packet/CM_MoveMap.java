package game.scene.map.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午9:58
 */

public class CM_MoveMap {

	private long mapId;
	private int targetX;
	private int targetY ;


	public long getMapId() {
		return mapId;
	}

	public void setMapId(long mapId) {
		this.mapId = mapId;
	}

	public int getTargetX() {
		return targetX;
	}

	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}

	public int getTargetY() {
		return targetY;
	}

	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}

	@Override
	public String toString() {
		return "CM_MoveMap{" +
				"mapId=" + mapId +
				", targetX=" + targetX +
				", targetY=" + targetY +
				'}';
	}
}
