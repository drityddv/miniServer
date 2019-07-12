package game.role.skill.model;

/**
 * 技能实体
 *
 * @author : ddv
 * @since : 2019/7/11 6:09 PM
 */

public class SkillEntry {

    private long skillId;
    private int level;

    public static SkillEntry valueOf(long skillId, int level) {
        SkillEntry entry = new SkillEntry();
        entry.skillId = skillId;
        entry.level = level;
        return entry;
    }

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
