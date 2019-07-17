package game.world.fight.packet;

import java.util.List;

/**
 * 群体指向性技能
 *
 * @author : ddv
 * @since : 2019/7/16 9:32 PM
 */

public class CM_UseGroupPointSkill {
    private long skillId;
    private List<Long> targetIdList;

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public List<Long> getTargetIdList() {
        return targetIdList;
    }

    public void setTargetIdList(List<Long> targetIdList) {
        this.targetIdList = targetIdList;
    }
}
