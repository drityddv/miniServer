package game.map.area;

import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.base.AbstractMovableScene;
import game.world.fight.model.BattleParam;

/**
 *
 * 目标收集器[范围收集,id收集等...]
 *
 * @author : ddv
 * @since : 2019/7/22 12:28 PM
 */

public abstract class BaseUnitCollector {

    /**
     * 计算区域单位集合
     *
     * @param battleParam
     * @param mapScene
     * @return
     */
    public abstract List<BaseCreatureUnit> doCollect(BattleParam battleParam, AbstractMovableScene mapScene);

    public void collectUnits(BattleParam battleParam) {
        battleParam.setTargetUnits(doCollect(battleParam, battleParam.getMapScene()));
    }
}
