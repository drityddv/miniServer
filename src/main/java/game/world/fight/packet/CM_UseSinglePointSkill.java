package game.world.fight.packet;

/**
 * 单体指向性技能
 *
 * @author : ddv
 * @since : 2019/7/16 9:32 PM
 */

public class CM_UseSinglePointSkill {
    private long skillId;
    private long targetId;

    public long getSkillId() {
        return skillId;
    }

    public long getTargetId() {
        return targetId;
    }
}
