package game.base.skill.model;

import java.util.List;
import java.util.Map;

import game.base.buff.model.BuffTypeEnum;
import game.base.skill.constant.SkillTypeEnum;
import game.base.skill.resource.SkillLevelResource;
import game.base.skill.resource.SkillResource;
import game.role.skill.service.SkillManager;

/**
 * @author : ddv
 * @since : 2019/7/15 8:41 PM
 */

public abstract class BaseSkill implements Cloneable {
    protected long skillId;
    protected int skillLevel;
    protected long lastUsedAt;
    protected SkillResource skillResource;
    protected SkillLevelResource skillLevelResource;

    public void init(long skillId, int skillLevel) {
        this.skillId = skillId;
        this.skillLevel = skillLevel;
        this.skillResource = SkillManager.getInstance().getSkillResource(skillId);
        this.skillLevelResource = SkillManager.getInstance().getSkillLevelResource(skillId, skillLevel);
    }

    // 技能耗蓝数值
    public int getSkillMpConsume() {
        return skillLevelResource.getMpConsume();
    }

    // 技能cd
    public long getSkillCd() {
        return skillLevelResource.getCd();
    }

    public Map<BuffTypeEnum, List<Long>> getBuffEffectMap() {
        return skillLevelResource.getBuffEffectMap();
    }

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public SkillResource getSkillResource() {
        return skillResource;
    }

    public void setSkillResource(SkillResource skillResource) {
        this.skillResource = skillResource;
    }

    public long getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(long lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public SkillLevelResource getSkillLevelResource() {
        return skillLevelResource;
    }

    public void setSkillLevelResource(SkillLevelResource skillLevelResource) {
        this.skillLevelResource = skillLevelResource;
    }

    public long getSkillValue() {
        return skillLevelResource.getValue();
    }

    public SkillTypeEnum getSkillType() {
        return this.skillLevelResource.getSkillTypeEnum();
    }
}
