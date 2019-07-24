package game.map.area;

import java.util.ArrayList;
import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.base.AbstractMovableScene;
import game.map.model.Grid;

/**
 *
 * 基本的范围处理器[圆形,放行,扇形等...] 收集作用范围的坐标点
 *
 * @author : ddv
 * @since : 2019/7/22 12:28 PM
 */

public abstract class BaseAreaProcess {
    /**
     * 阻挡点信息带过来吧 方便以后拓展选择逻辑
     */
    protected AbstractMovableScene mapScene;
    protected int[][] blockData;
    protected List<Grid> targetGrids = new ArrayList<>();
    protected List<BaseCreatureUnit> targetUnits = new ArrayList<>();

    public List<BaseCreatureUnit> getResult() {
        calculate();
        return targetUnits;
    }

    public void init(AreaProcessParam processParam, AbstractMovableScene mapScene) {

    }

    /**
     * 计算影响的坐标点
     */
    public abstract void calculate();
}
