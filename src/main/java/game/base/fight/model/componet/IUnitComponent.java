package game.base.fight.model.componet;

import game.base.fight.model.pvpunit.BaseUnit;

/**
 * 战斗组件
 *
 * @author : ddv
 * @since : 2019/7/5 下午4:06
 */

public interface IUnitComponent<T extends BaseUnit> {

    /**
     * 获取组件类型
     *
     * @return
     */
    UnitComponentType getType();

    /**
     * 设置组件归属
     *
     * @param unit
     */
    void setOwner(T unit);

    /**
     * 重置组件信息
     */
    default void reset() {

    }

    /**
     * 清除组件信息
     */
    default void clear() {

    }
}
