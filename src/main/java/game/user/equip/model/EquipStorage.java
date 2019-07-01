package game.user.equip.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.id.AttributeIdEnum;
import game.user.equip.constant.EquipPosition;
import game.user.player.model.Player;

/**
 * 普通装备栏
 *
 * @author : ddv
 * @since : 2019/6/28 上午11:57
 */

public class EquipStorage {

    private static Logger logger = LoggerFactory.getLogger(EquipStorage.class);

    private static final int MAX_SIZE = EquipPosition.values().length;

    /**
     * 1 :头盔 2:铠甲 3:武器
     */

    private Map<Integer, EquipSquare> equipSlotMap;

    public static EquipStorage valueOf() {
        EquipStorage equipStorage = new EquipStorage();
        equipStorage.equipSlotMap = new HashMap<>(MAX_SIZE);
        for (EquipPosition equipPosition : EquipPosition.values()) {
            EquipSquare square = EquipSquare.valueOf(equipPosition);
            equipStorage.equipSlotMap.put(equipPosition.getId(), square);
        }
        return equipStorage;
    }

    public boolean isSquareEmpty(int index) {
        return equipSlotMap.get(index).isEmpty();
    }

    public void addEquip(Equipment equipment, EquipPosition equipPosition) {
        EquipSquare equipSquare = equipSlotMap.get(equipPosition.getId());
        equipSquare.setEquipment(equipment);
        equipSquare.reSetAttrs();
    }

    public EquipSquare getEquipSquare(int position) {
        return equipSlotMap.get(EquipPosition.getPosition(position).getId());
    }

    // 重新统计属性
    public void reCompute(Player player) {
        List<Attribute> attributes = getCurrentAttribute();
        player.getAttributeContainer().putAttributesWithRecompute(AttributeIdEnum.BASE_EQUIPMENT, attributes, true);
    }

    public List<Attribute> getCurrentAttribute() {
        List<Attribute> attributes = new ArrayList<>();

        for (EquipSquare equipSquare : equipSlotMap.values()) {
            attributes.addAll(equipSquare.getCurrentAttribute());
        }
        return attributes;
    }

    public static int getMaxSize() {
        return MAX_SIZE;
    }

    // get and set

    public Map<Integer, EquipSquare> getEquipSlotMap() {
        return equipSlotMap;
    }

    public void setEquipSlotMap(Map<Integer, EquipSquare> equipSlotMap) {
        this.equipSlotMap = equipSlotMap;
    }

    @Override
    public String toString() {
        return "EquipStorage{" + "equipSlotMap=" + equipSlotMap + '}';
    }
}
