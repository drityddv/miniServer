package game.base.skill.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 技能类型[单体指向,群体指向,aoe等...]
 *
 * @author : ddv
 * @since : 2019/7/22 5:13 PM
 */

public enum SkillTypeEnum {
    // 技能说明
    Single_Point("单体指向性"),

    Group_Point("群体指向性"),

    Aoe("范围技能"),

    Self("自身释放");
    private static Map<String, SkillTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (SkillTypeEnum anEnum : SkillTypeEnum.values()) {
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
    }

    private String typeName;

    SkillTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public static SkillTypeEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }
}
