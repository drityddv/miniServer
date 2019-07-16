package game.role.skill.packet;

/**
 * @author : ddv
 * @since : 2019/7/16 6:25 PM
 */

public class CM_AddSkillToSquare {
    private long skillId;
    private int squareIndex;

    public long getSkillId() {
        return skillId;
    }

    public int getSquareIndex() {
        return squareIndex;
    }
}
