package game.role.skill.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 玩家技能存储实体
 *
 * @author : ddv
 * @since : 2019/7/11 8:17 PM
 */

public class SkillList {

    /**
     * 玩家技能集合[技能配置id - 技能]
     */
    private Map<Long, SkillEntry> skills = new HashMap<>();

    /**
     * 技能槽[玩家自定义的快捷施法栏]
     */
    private Map<Integer, SkillSquare> skillSquareMap = new HashMap<>();

    public static SkillList valueOf() {
        SkillList skillList = new SkillList();
        return skillList;
    }

    // 是否学习了某技能
    public boolean isLearnedSkill(long skillId) {
        return skills.containsKey(skillId);
    }

    public void addSkill(SkillEntry skillEntry) {
        skills.put(skillEntry.getSkillId(), skillEntry);
    }

    public SkillEntry getSkillEntry(long skillId) {
        return skills.get(skillId);
    }

    // 技能槽的修改要保证是同一个引用
    public void levelUp(long skillId, int level) {
        SkillEntry skillEntry = skills.get(skillId);
        skillEntry.setLevel(level);
    }

    // get and set
    public Map<Long, SkillEntry> getSkills() {
        return skills;
    }

    public void setSkills(Map<Long, SkillEntry> skills) {
        this.skills = skills;
    }

    public Map<Integer, SkillSquare> getSkillSquareMap() {
        return skillSquareMap;
    }

    public void setSkillSquareMap(Map<Integer, SkillSquare> skillSquareMap) {
        this.skillSquareMap = skillSquareMap;
    }
}
