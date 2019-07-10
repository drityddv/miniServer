package game.role.equip.constant;

import java.util.HashMap;
import java.util.Map;

import game.base.message.I18N;
import game.base.message.exception.RequestException;

/**
 * @author : ddv
 * @since : 2019/6/28 上午11:35
 */

public enum EquipPosition {
    /**
     * 头盔
     */
    HAT(1, "头盔", 4),
    /**
     * 铠甲
     */
    CLOTHES(2, "铠甲", 7),
    /**
     * 武器
     */
    WEAPON(3, "武器", 1),;

    private static final Map<Integer, EquipPosition> ID_TO_POSITION = new HashMap<>(EquipPosition.values().length);

    private static final Map<String, EquipPosition> NAME_TO_POSITION = new HashMap<>(EquipPosition.values().length);

    static {
        for (EquipPosition equipPosition : EquipPosition.values()) {
            ID_TO_POSITION.put(equipPosition.id, equipPosition);
            NAME_TO_POSITION.put(equipPosition.name(), equipPosition);
        }
    }

    /**
     * 装备的位置id
     */
    private int id;
    /**
     * 装备位置
     */
    private String position;
    /**
     * 这个对应强化表中初始等级的配置id
     */
    private int configId;

    EquipPosition(int id, String position, int configId) {
        this.id = id;
        this.position = position;
        this.configId = configId;
    }

    public static EquipPosition getPosition(int id) {
        EquipPosition equipPosition = ID_TO_POSITION.get(id);

        if (equipPosition == null) {
            RequestException.throwException(I18N.INDEX_ERROR);
        }
        return equipPosition;
    }

    public static EquipPosition getPosition(String name) {
        EquipPosition equipPosition = NAME_TO_POSITION.get(name);

        if (equipPosition == null) {
            RequestException.throwException(I18N.INDEX_ERROR);
        }

        return equipPosition;
    }

    public int getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public int getConfigId() {
        return configId;
    }

    @Override
    public String toString() {
        return "EquipPosition{" + "id=" + id + ", position='" + position + '\'' + '}';
    }
}
