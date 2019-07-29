package game.base.buff.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/24 10:51 AM
 */

public enum BuffParamEnum {
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
    /**
     * 回蓝数值
     */
    CureMp(Long.class),
    /**
     * buffId
     */
    Buff_Id(Long.class),
    /**
     * 触发点
     */
    Trigger_Point,

    ;

    private static Map<String, BuffParamEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (BuffParamEnum anEnum : BuffParamEnum.values()) {
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
    }

    private Class paramClazz;

    BuffParamEnum() {

    }

    BuffParamEnum(Class paramClazz) {
        this.paramClazz = paramClazz;
    }

    public static BuffParamEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public Class getParamClazz() {
        return paramClazz;
    }
}
