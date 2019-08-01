package game.world.instance.model.hatch;

import java.util.List;

import game.map.visible.AbstractMapObject;

/**
 * 地图单位刷新器 [刷新一批单位]
 *
 * @author : ddv
 * @since : 2019/7/30 10:21 PM
 */

public abstract class BaseMapObjectHatch {
    /**
     * 孵化单位
     *
     * @param param
     * @return
     */
    public abstract List<AbstractMapObject> hatch(HatchParam param);
}
