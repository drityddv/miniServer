package game.base.game.attribute;

/**
 * @author : ddv
 * @since : 2019/6/20 下午10:21
 */

public enum AttributeType {
    /**
     * 生命值
     */
    HP(1, "生命值"),
    /**
     * 法力值
     */
    MANA(2, "法力值"),
    /**
     * 物理护甲
     */
    Physical_ARMOR(3, "物理护甲"),
    /**
     * 法术护甲
     */
    MAGIC_ARMOR(4, "魔法护甲"),;

    private int typeId;
    private String typeName;

    AttributeType(int id, String typeName) {
        this.typeId = id;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }
}
