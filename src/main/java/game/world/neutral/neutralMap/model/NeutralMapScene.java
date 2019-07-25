package game.world.neutral.neutralMap.model;

import game.map.base.AbstractMovableScene;
import game.map.base.BaseMapInfo;
import game.map.base.MapAoiManager;
import game.map.visible.PlayerMapObject;

/**
 * 中立地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午3:57
 */

public class NeutralMapScene extends AbstractMovableScene<PlayerMapObject> {

    public NeutralMapScene(int mapId) {
        super(mapId);
    }

    public static NeutralMapScene valueOf(int mapId, BaseMapInfo baseMapInfo) {
        NeutralMapScene mapScene = new NeutralMapScene(mapId);
        mapScene.baseMapInfo = baseMapInfo;
        mapScene.aoiManager = MapAoiManager.valueOf(baseMapInfo);
        return mapScene;
    }

}
