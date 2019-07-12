package game.role.skill.packet;

import game.role.skill.model.SkillList;

/**
 * @author : ddv
 * @since : 2019/7/12 2:39 PM
 */

public class SM_SkillListVo {

    private SkillList skillList;

    public static SM_SkillListVo valueOf(SkillList skillList) {
        SM_SkillListVo sm = new SM_SkillListVo();
        sm.skillList = skillList;
        return sm;
    }

    public SkillList getSkillList() {
        return skillList;
    }

    public void setSkillList(SkillList skillList) {
        this.skillList = skillList;
    }
}
