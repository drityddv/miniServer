package game.base.fight.model.skill.action.handler;

import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * 技能动作处理
 *
 * @author : ddv
 * @since : 2019/7/16 9:57 AM
 */

public interface IActionHandler {

    /**
     *
     *
     * @param caster
     * @param defender
     * @param skillId
     */
    void action(BaseCreatureUnit caster, BaseCreatureUnit defender, long skillId);
}
