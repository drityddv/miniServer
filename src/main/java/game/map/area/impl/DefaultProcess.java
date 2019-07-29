package game.map.area.impl;

import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.area.AreaProcessParam;
import game.map.area.BaseAreaProcess;
import game.map.base.AbstractMovableScene;

/**
 * @author : ddv
 * @since : 2019/7/29 5:19 PM
 */

public class DefaultProcess extends BaseAreaProcess {
    @Override
    public List<BaseCreatureUnit> calculate(AreaProcessParam param, AbstractMovableScene mapScene) {
        List<Long> targetIdList = param.getTargetIdList();
        List mapUnits = mapScene.getMapUnits(targetIdList);
        return mapUnits;
    }
}
