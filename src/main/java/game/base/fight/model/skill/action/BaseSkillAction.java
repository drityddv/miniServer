package game.base.fight.model.skill.action;

import game.base.skill.constant.SkillType;
import game.base.skill.model.BaseSkill;

/**
 * @author : ddv
 * @since : 2019/7/16 11:55 AM
 */

public class BaseSkillAction {

    protected SkillType skillType;
    protected BaseSkill baseSkill;

    public BaseSkillAction(SkillType skillType, BaseSkill baseSkill) {
        this.skillType = skillType;
        this.baseSkill = baseSkill;
    }
}
