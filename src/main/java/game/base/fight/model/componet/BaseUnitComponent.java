package game.base.fight.model.componet;

import game.base.fight.model.pvpunit.BaseUnit;

/**
 * 抽象组件[]
 *
 * @author : ddv
 * @since : 2019/7/15 10:28 AM
 */

public abstract class BaseUnitComponent<T extends BaseUnit> implements IUnitComponent<T> {

    protected transient T owner;

    public T getOwner() {
        return owner;
    }

    @Override
    public void setOwner(T owner) {
        this.owner = owner;
    }
}
