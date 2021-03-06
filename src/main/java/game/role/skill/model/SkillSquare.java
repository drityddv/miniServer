package game.role.skill.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 技能槽[实战中的技能列表载体,支持玩家自定义]
 *
 * @author : ddv
 * @since : 2019/7/11 8:25 PM
 */

public class SkillSquare {
    // 技能栏序号
    private int squareIndex;
    // 技能栏技能信息
    private Map<Long, SkillEntry> squareSkills;

    public static SkillSquare valueOf(int index) {
        SkillSquare square = new SkillSquare();
        square.squareIndex = index;
        square.squareSkills = new HashMap<>();
        return square;
    }

    public static SkillSquare valueOf(SkillSquare square) {
        SkillSquare skillSquare = new SkillSquare();
        skillSquare.squareIndex = square.squareIndex;
        skillSquare.squareSkills = new HashMap<>();

        square.getSquareSkills().forEach((skillId, skillEntry) -> {
            skillSquare.squareSkills.put(skillId, SkillEntry.valueOf(skillEntry.getSkillId(), skillEntry.getLevel()));
        });

        return square;
    }

    public void init(Map<Long, SkillEntry> skillEntryMap) {
        squareSkills.keySet().forEach(skillId -> {
            squareSkills.put(skillId, skillEntryMap.get(skillId));
        });
    }

    public void addSkillEntry(SkillEntry skillEntry) {
        squareSkills.put(skillEntry.getSkillId(), skillEntry);
    }

    public int getSquareIndex() {
        return squareIndex;
    }

    public Map<Long, SkillEntry> getSquareSkills() {
        return squareSkills;
    }

    public boolean containSkill(long skillId) {
        return squareSkills.containsKey(skillId);
    }
}
