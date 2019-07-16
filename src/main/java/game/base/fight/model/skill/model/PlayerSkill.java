package game.base.fight.model.skill.model;

import game.base.skill.model.BaseSkill;
import game.role.skill.model.SkillEntry;
import game.role.skill.service.SkillManager;

/**
 *
 * @author : ddv
 * @since : 2019/7/16 10:37 AM
 */

public class PlayerSkill extends BaseSkill {

    public static PlayerSkill valueOf(SkillEntry skillEntry) {
        PlayerSkill playerSkill = new PlayerSkill();
        playerSkill.skillId = skillEntry.getSkillId();
        playerSkill.skillLevel = skillEntry.getLevel();
        playerSkill.skillResource = SkillManager.getInstance().getSkillResource(skillEntry.getSkillId());
        playerSkill.skillLevelResource =
            SkillManager.getInstance().getSkillLevelResource(skillEntry.getSkillId(), skillEntry.getLevel());
        return playerSkill;
    }
}
