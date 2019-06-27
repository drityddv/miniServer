package game.base.game.attribute;

/**
 *
 * 1 : 基础属性 2 : 空位 3 : 百分比属性
 *
 * @author : ddv
 * @since : 2019/6/26 下午5:49
 */

public class AttributeTypeConst {

    /**
     * 所基础属性集合
     */
    public static final AttributeType[] BASE_LIST = new AttributeType[] {AttributeType.PHYSICAL_ATTACK_LOWER,
        AttributeType.PHYSICAL_ATTACK_UPPER, AttributeType.MAGIC_ATTACK_LOWER, AttributeType.MAGIC_ATTACK_UPPER,
        AttributeType.MAX_HP, AttributeType.MAX_MP};

    /**
     * 攻击属性集合
     */
    public static final AttributeType[] ATTACK_LIST =
        new AttributeType[] {AttributeType.BASIC_ATTACK, AttributeType.PHYSICAL_ATTACK_LOWER,
            AttributeType.PHYSICAL_ATTACK_UPPER, AttributeType.MAGIC_ATTACK_LOWER, AttributeType.MAGIC_ATTACK_UPPER};

    /**
     * 物理攻击集合
     */
    public static final AttributeType[] PHYSICAL_ATTACK_LIST = new AttributeType[] {AttributeType.BASIC_ATTACK,
        AttributeType.PHYSICAL_ATTACK_LOWER, AttributeType.PHYSICAL_ATTACK_UPPER};

    /**
     * 法术攻击集合
     */
    public static final AttributeType[] MAGIC_ATTACK_LIST = new AttributeType[] {AttributeType.BASIC_ATTACK,
        AttributeType.MAGIC_ATTACK_LOWER, AttributeType.MAGIC_ATTACK_UPPER};

    /**
     * 防御属性集合
     */
    public static final AttributeType[] DEFENSE_LIST =
        new AttributeType[] {AttributeType.PHYSICAL_ARMOR, AttributeType.MAGIC_ARMOR};

    /**
     * 物理防御集合
     */
    public static final AttributeType[] PHYSICAL_DEFENSE_LIST = new AttributeType[] {AttributeType.PHYSICAL_ARMOR};

    /**
     * 法术防御集合
     */
    public static final AttributeType[] MAGIC_DEFENSE_LIST = new AttributeType[] {AttributeType.MAGIC_ARMOR};
}
