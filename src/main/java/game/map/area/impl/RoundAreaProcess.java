package game.map.area.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.area.AreaProcessParam;
import game.map.area.BaseAreaProcess;
import game.map.base.AbstractMovableScene;
import game.map.model.Grid;
import game.map.visible.AbstractMapObject;
import game.map.visible.BaseAttackAbleMapObject;
import game.world.utils.MapUtil;

/**
 * 计算逻辑:算出左上和右下 然后锁定矩形区域 再计算内部坐标点的距离
 *
 * @author : ddv
 * @since : 2019/7/22 2:11 PM
 */

public class RoundAreaProcess extends BaseAreaProcess {

    @Override
    public List<BaseCreatureUnit> calculate(AreaProcessParam param, AbstractMovableScene mapScene) {
        List<BaseCreatureUnit> units = new ArrayList<>();
        Grid center = param.getCenter();
        int radius = param.getRadius();
        List<AbstractMapObject> areaObjects = mapScene.getAreaObjects(center);
        areaObjects = areaObjects.stream()
            .filter(mapObject -> mapObject instanceof BaseAttackAbleMapObject
                && MapUtil.calculateDistance(center, mapObject.getCurrentGrid()) <= radius)
            .collect(Collectors.toList());
        areaObjects.forEach(mapObject -> {
            units.add(mapObject.getFighterAccount().getCreatureUnit());
        });
        return units;
    }

}
