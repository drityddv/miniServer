package game.base.skill.resource;

import resource.anno.Init;
import resource.anno.MiniResource;

/**
 * @author : ddv
 * @since : 2019/7/11 6:16 PM
 */
@MiniResource
public class SkillResource {
    /**
     * 配置id
     */
    private long skillId;
    /**
     * 技能名
     */
    private String skillName;

    private long initLevelConfigId;

    @Init
    public void init() {

    }

    public long getSkillId() {
        return skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public long getInitLevelConfigId() {
        return initLevelConfigId;
    }
}
