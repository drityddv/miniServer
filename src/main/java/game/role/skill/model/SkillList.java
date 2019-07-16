package game.role.skill.model;

import java.util.HashMap;
import java.util.Map;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.role.skill.constant.SkillConstant;

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
     * 默认技能栏index
     */
    private int defaultSquareIndex;

    /**
     * 技能槽[玩家自定义的快捷施法栏]
     */
    private Map<Integer, SkillSquare> skillSquareMap = new HashMap<>(SkillConstant.MAX_SQUARE_INDEX);

    public SkillList() {
        skillSquareMap.values().forEach(skillSquare -> skillSquare.init(skills));
    }

    public static SkillList valueOf() {
        SkillList skillList = new SkillList();
        skillList.defaultSquareIndex = 1;
        for (int i = 1; i <= SkillConstant.MAX_SQUARE_INDEX; i++) {
            skillList.skillSquareMap.put(i, SkillSquare.valueOf(i));
        }
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

    // 这里会check下标
    public SkillSquare getSquare(int squareIndex) {
        if (squareIndex <= 0 || squareIndex > SkillConstant.MAX_SQUARE_INDEX) {
            RequestException.throwException(I18N.INDEX_ERROR);
        }
        return skillSquareMap.get(squareIndex);
    }

    // get and set
    public Map<Long, SkillEntry> getSkills() {
        return skills;
    }

    // 获取格子不要调用这个方法
    public Map<Integer, SkillSquare> getSkillSquareMap() {
        return skillSquareMap;
    }

    public int getDefaultSquareIndex() {
        return defaultSquareIndex;
    }

    public void setDefaultSquareIndex(int defaultSquareIndex) {
        this.defaultSquareIndex = defaultSquareIndex;
    }

    public SkillSquare getDefaultSquare() {
        return skillSquareMap.get(defaultSquareIndex);
    }
}
