package game.base.effect.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/24 10:51 AM
 */

public enum BuffContextParamEnum {
    /**
     * 毒素层数
     */
    POISON_LEVEL(Integer.class),
    /**
     * 毒素伤害
     */
    POISON_DAMAGE(Long.class),
    /**
     * 使用者
     */
    CASTER,
    /**
     * 作用目标
     */
    Target,
    /**
     * 作用目标集合
     */
    Target_List,
    /**
     * 属性容器
     */
    Attribute_Container,
    /**
     * 属性集合
     */
    Attribute,
    /**
     * 最大生命值
     */
    MAX_HP(Long.class),
    /**
     * 最大法力值
     */
    MAX_MP(Long.class),
    /**
     * 回血数值
     */
    CureHp(Long.class),

    Buff_Id(Long.class),

	Trigger_Point,


	;

    private static Map<String, BuffContextParamEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (BuffContextParamEnum anEnum : BuffContextParamEnum.values()) {
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
    }

    private Class paramClazz;

    BuffContextParamEnum() {

    }

    BuffContextParamEnum(Class paramClazz) {
        this.paramClazz = paramClazz;
    }

    public static BuffContextParamEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public Class getParamClazz() {
        return paramClazz;
    }
}
