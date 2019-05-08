package game.scene.map.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午9:43
 */

public class CM_TransferMap {

	private long mapId;

	public long getMapId() {
		return mapId;
	}

	public void setMapId(long mapId) {
		this.mapId = mapId;
	}

	@Override
	public String toString() {
		return "CM_TransferMap{" +
				"mapId=" + mapId +
				'}';
	}
}
