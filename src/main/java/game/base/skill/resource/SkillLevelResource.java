package game.base.skill.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.base.consume.AssetsConsume;
import game.base.consume.IConsume;
import game.base.game.attribute.AttributeType;
import game.base.skill.constant.SkillEnum;
import resource.anno.Init;
import resource.anno.MiniResource;
import utils.StringUtil;

/**
 * @author : ddv
 * @since : 2019/7/12 11:33 AM
 */
@MiniResource
public class SkillLevelResource {
    /**
     * 配置表id
     */
    private long configId;
    /**
     * 技能id
     */
    private long skillId;
    private int level;
    /**
     * 技能效果数值
     */
    private long value;
    private int mpConsume;
    // 单位毫秒 配置表中为秒
    private long cd;
    private String cdString;
    private int nextLevelConfigId;
    /**
     * 升级消耗
     */
    private List<IConsume> consumes;
    private String consumeString;
    /**
     * 效果id
     */
    private Set<Long> effectIds;
    private String effectIdString;

    /**
     * 技能type
     */
    private SkillEnum skillEnum;
    private String skillEnumId;

    /**
     * 计算技能伤害时关联的属性
     */
    private List<AttributeType> attributeTypes;
    private String attributeTypeString;

    @Init
    public void init() {
        analysisCd();
        analysisSkillType();
        analysisConsume();
        analysisEffectIdSet();
        analysisAttributeTypes();
    }

    private void analysisAttributeTypes() {
        attributeTypes = new ArrayList<>();
        if (StringUtil.isNotEmpty(attributeTypeString)) {
            for (String typeString : attributeTypeString.split(",")) {
                attributeTypes.add(AttributeType.getByName(typeString));
            }
        }
    }

    private void analysisSkillType() {
        if (StringUtil.isNotEmpty(skillEnumId)) {
            skillEnum = SkillEnum.getById(Integer.parseInt(skillEnumId));
        }
    }

    private void analysisCd() {
        cd = (long)(Double.parseDouble(cdString) * 1000);
    }

    // 解析技能效果
    private void analysisEffectIdSet() {
        effectIds = new HashSet<>();
        if (StringUtil.isNotEmpty(effectIdString)) {
            String[] split = effectIdString.split(",");
            for (String value : split) {
                effectIds.add(Long.parseLong(value));
            }
        }
    }

    // 解析技能升级消耗
    private void analysisConsume() {
        consumes = new ArrayList<>();
        String[] split = consumeString.split(",");
        for (String value : split) {
            consumes.add(AssetsConsume.valueOf(value));
        }
    }

    public long getConfigId() {
        return configId;
    }

    public long getSkillId() {
        return skillId;
    }

    public int getLevel() {
        return level;
    }

    public int getNextLevelConfigId() {
        return nextLevelConfigId;
    }

    public List<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public List<IConsume> getConsumes() {
        return consumes;
    }

    public Set<Long> getEffectIds() {
        return effectIds;
    }

    public long getCd() {
        return cd;
    }

    public SkillEnum getSkillEnum() {
        return skillEnum;
    }

    public int getMpConsume() {
        return mpConsume;
    }

    public long getValue() {
        return value;
    }
}
