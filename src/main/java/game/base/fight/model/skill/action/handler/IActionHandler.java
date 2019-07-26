package game.base.fight.model.skill.action.handler;

import game.world.fight.model.BattleParam;

/**
 * 技能动作处理
 *
 * @author : ddv
 * @since : 2019/7/16 9:57 AM
 */

public interface IActionHandler {

    /**
     * 处理战斗
     *
     * @param battleParam
     */
    void action(BattleParam battleParam);
}
