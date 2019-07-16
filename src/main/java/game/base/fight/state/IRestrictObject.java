package game.base.fight.state;

import java.util.Set;

import game.base.effect.model.constant.RestrictStatusEnum;

/**
 * 限制性buff
 *
 * @author : ddv
 * @since : 2019/7/15 8:05 PM
 */

public interface IRestrictObject {

    /**
     * 获取对应的限制状态
     *
     * @return
     */
    Set<RestrictStatusEnum> getRestrictStatus();

}
