package game.user.item.base.constant;

import java.util.HashMap;
import java.util.Map;

import game.user.item.base.model.AbstractItem;
import game.user.item.base.model.Elixir;
import game.user.item.base.model.Medicine;

/**
 * 道具类型
 *
 * @author : ddv
 * @since : 2019/6/25 下午3:47
 */

public enum ItemType {
    /**
     * 药水
     */
    MEDICINE(1, Medicine.class),

    /**
     * 经验仙丹
     */
    ELIXIR(2, Elixir.class);

    // 道具类型id
    private int typeId;
    // 道具类
    private Class<? extends AbstractItem> typeClass;

    ItemType(int typeId, Class<? extends AbstractItem> typeClass) {
        this.typeId = typeId;
        this.typeClass = typeClass;
    }

    private final static Map<String, ItemType> NAME_TO_TYPE = new HashMap<>();

    static {
        for (ItemType itemType : ItemType.values()) {
            NAME_TO_TYPE.put(itemType.name(), itemType);
        }
    }

    public static ItemType getTypeByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public AbstractItem create() {
        try {
            return typeClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // get and set
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(Class typeClass) {
        this.typeClass = typeClass;
    }
}
