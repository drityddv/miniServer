package game.base.effect.model.impl;

import java.util.EnumSet;
import java.util.Set;

import game.base.effect.model.BaseBuffEffect;
import game.base.effect.model.constant.RestrictStatusEnum;
import game.base.fight.state.IRestrictObject;

/**
 * 眩晕技能
 *
 * @author : ddv
 * @since : 2019/7/15 2:53 PM
 */

public class DizzyBuffEffect extends BaseBuffEffect implements IRestrictObject {

    @Override
    public void merge(BaseBuffEffect buffEffect) {
        if (buffEffect instanceof DizzyBuffEffect) {
            DizzyBuffEffect dizzyBuffEffect = (DizzyBuffEffect)buffEffect;
            this.mergedCount++;
        }
    }

    @Override
    public void active() {

    }

    @Override
    public Set<RestrictStatusEnum> getRestrictStatus() {
        return EnumSet.of(RestrictStatusEnum.FORBID_MOVE);
    }
}
