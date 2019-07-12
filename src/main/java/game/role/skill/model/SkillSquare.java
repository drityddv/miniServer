package game.role.skill.model;

import java.util.Map;

/**
 * 技能槽[实战中的技能列表载体,支持玩家自定义]
 *
 * @author : ddv
 * @since : 2019/7/11 8:25 PM
 */

public class SkillSquare {
	//技能栏序号
    private int squareIndex;
    // 技能栏技能信息
    private Map<Integer, SkillEntry> skillList;
}
