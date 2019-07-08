package game.user.item.base.constant;

import java.util.HashMap;
import java.util.Map;

import game.role.equip.model.Equipment;
import game.user.item.base.model.Elixir;
import game.user.item.base.model.ExchangeCard;
import game.user.item.base.model.Medicine;
import game.user.item.base.model.Stone;

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
    ELIXIR(2, Elixir.class),

    /**
     * 兑换卡
     */
    EXCHANGE_CARD(3, ExchangeCard.class),

    /**
     * 装备
     */
    EQUIPMENT(4, Equipment.class),

    /**
     * 石头
     */
    STONE(5, Stone.class),;

    private final static Map<String, ItemType> NAME_TO_TYPE = new HashMap<>();

    static {
        for (ItemType itemType : ItemType.values()) {
            NAME_TO_TYPE.put(itemType.name(), itemType);
        }
    }

    // 道具类型id
    private int typeId;
    // 道具类
    private Class<? extends game.user.item.base.model.AbstractItem> typeClass;

    ItemType(int typeId, Class<? extends game.user.item.base.model.AbstractItem> typeClass) {
        this.typeId = typeId;
        this.typeClass = typeClass;
    }

    public static ItemType getTypeByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public game.user.item.base.model.AbstractItem create() {
        try {
            return typeClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
