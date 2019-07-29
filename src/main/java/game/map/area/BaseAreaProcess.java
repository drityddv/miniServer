package game.map.area;

import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.base.AbstractMovableScene;
import game.map.model.Grid;
import game.world.fight.model.BattleParam;

/**
 *
 * 基本的范围处理器[圆形,放行,扇形等...] 收集作用范围的坐标点
 *
 * @author : ddv
 * @since : 2019/7/22 12:28 PM
 */

public abstract class BaseAreaProcess {

    /**
     * 计算区域单位集合
     *
     * @param battleParam
     * @param mapScene
     * @return
     */
    public abstract List<BaseCreatureUnit> calculate(BattleParam battleParam, AbstractMovableScene mapScene);

    public void loadTargets(BattleParam battleParam, Grid center) {
        battleParam.setTargetUnits(calculate(battleParam, battleParam.getMapScene()));
    }
}
