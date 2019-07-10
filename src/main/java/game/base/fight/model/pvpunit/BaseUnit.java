package game.base.fight.model.pvpunit;

import game.base.fight.model.componet.UnitComponentContainer;
import game.map.model.Grid;

/**
 * pvp对象基础单元
 *
 * @author : ddv
 * @since : 2019/7/5 下午2:20
 */

public abstract class BaseUnit {
    protected UnitComponentContainer componentContainer = new UnitComponentContainer();
    private long id;
    private int level;
    private Grid grid;

    protected BaseUnit(long id) {
        this.id = id;
    }

    public void initComponent() {
        componentContainer.initialize(this);
    }

    // get and set
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public UnitComponentContainer getComponentContainer() {
        return componentContainer;
    }
}
