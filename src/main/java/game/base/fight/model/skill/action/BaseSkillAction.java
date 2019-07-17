package game.base.fight.model.skill.action;

import game.base.skill.constant.SkillEnum;
import game.base.skill.model.BaseSkill;

/**
 * @author : ddv
 * @since : 2019/7/16 11:55 AM
 */

public class BaseSkillAction {

    protected SkillEnum skillEnum;
    protected BaseSkill baseSkill;

    public BaseSkillAction(SkillEnum skillEnum, BaseSkill baseSkill) {
        this.skillEnum = skillEnum;
        this.baseSkill = baseSkill;
    }
}
