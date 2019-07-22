package game.base.fight.model.skill.action.handler;

import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;
import game.map.model.Grid;

/**
 * 技能动作处理
 *
 * @author : ddv
 * @since : 2019/7/16 9:57 AM
 */

public interface IActionHandler {

    /**
     * 单体指向性技能
     *
     * @param caster
     * @param defender
     * @param baseSkill
     */
    void action(BaseCreatureUnit caster, BaseCreatureUnit defender, BaseSkill baseSkill);

    /**
     * 群体指向性技能
     *
     * @param caster
     * @param target
     * @param baseSkill
     */
    void action(BaseCreatureUnit caster, List<BaseCreatureUnit> target, BaseSkill baseSkill);

    /**
     * aoe技能
     *
     * @param caster
     * @param center
     * @param baseSkill
     */
    void action(BaseCreatureUnit caster, Grid center, BaseSkill baseSkill);
}
