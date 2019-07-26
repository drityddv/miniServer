package game.world.fight.packet;

/**
 * aoe技能指令
 *
 * @author : ddv
 * @since : 2019/7/22 4:59 PM
 */

public class CM_UserAoeSkill {
    private long SkillId;
    private int centerX;
    private int centerY;
    private int centerType;

    public int getCenterType() {
        return centerType;
    }

    public long getSkillId() {
        return SkillId;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }
}
