package game.base.fight.base.constant;

import game.base.fight.base.model.BaseActionEntry;
import game.base.fight.base.model.attack.impl.PhysicalSingleAttack;

/**
 * @author : ddv
 * @since : 2019/7/29 3:51 PM
 */

public enum BaseActionEnum {
    /**
     * 物理攻击
     */
    Physical_Attack(PhysicalSingleAttack.class),;

    private Class<? extends BaseActionEntry> actionType;

    BaseActionEnum(Class<? extends BaseActionEntry> actionType) {
        this.actionType = actionType;
    }

    public void handlerActionResult() {

    }
}
