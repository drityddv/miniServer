package game.role.equip.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.id.AttributeIdEnum;
import game.base.game.attribute.util.AttributeUtils;
import game.role.equip.constant.EquipPosition;
import game.role.player.model.Player;

/**
 * 普通装备栏
 *
 * @author : ddv
 * @since : 2019/6/28 上午11:57
 */

public class EquipStorage {

    private static final int MAX_SIZE = EquipPosition.values().length;
    /**
     * 1 :头盔 2:铠甲 3:武器
     */
    private Map<Integer, EquipSquare> equipSquareMap;

    /**
     * 装备栏的最终属性
     */
    private transient Map<AttributeType, Attribute> storageAttributes = new HashMap<>();

    /**
     * 孔位的属性
     */
    private transient Map<Integer, AttributeSet> squareAttributes = new HashMap<>();

    public static EquipStorage valueOf() {
        EquipStorage equipStorage = new EquipStorage();
        equipStorage.equipSquareMap = new HashMap<>(MAX_SIZE);
        for (EquipPosition equipPosition : EquipPosition.values()) {
            EquipSquare square = EquipSquare.valueOf(equipPosition);
            equipStorage.equipSquareMap.put(equipPosition.getId(), square);
        }
        return equipStorage;
    }

    public boolean isSquareEmpty(EquipPosition equipPosition) {
        return equipSquareMap.get(equipPosition.getId()).isEmpty();
    }

    public void addEquip(Equipment equipment, EquipPosition equipPosition) {
        EquipSquare equipSquare = equipSquareMap.get(equipPosition.getId());
        equipSquare.setEquipment(equipment);
        equipSquare.refreshAttrs();
    }

    public EquipSquare getEquipSquare(EquipPosition equipPosition) {
        return equipSquareMap.get(equipPosition.getId());
    }

    // 重新统计属性
    public void reCompute(Player player) {
        List<Attribute> attributes = reComputeSquareAttrs();
        player.getAttributeContainer().putAttributesWithRecompute(AttributeIdEnum.BASE_EQUIPMENT, attributes, true);
    }

    // 计算对应位置的变动带来的属性变化
    public void reComputeTargetSquare(Player player, EquipPosition equipPosition) {
        EquipSquare equipSquare = equipSquareMap.get(equipPosition.getId());
        equipSquare.refreshAttrs();

        squareAttributes.put(equipSquare.getEquipPosition().getId(),
            AttributeSet.valueOf(equipSquare.getCurrentAttribute()));

        Map<AttributeType, Attribute> finalAttrs = new HashMap<>();
        AttributeUtils.accumulateSetToMap(squareAttributes.values(), finalAttrs);
        storageAttributes = finalAttrs;

        // 重新计算玩家属性容器
        player.getAttributeContainer().putAttributesWithRecompute(AttributeIdEnum.BASE_EQUIPMENT,
            AttributeUtils.wrapperToList(storageAttributes.values()), true);
    }

    // 重新统计所有属性
    public List<Attribute> reComputeSquareAttrs() {
        List<Attribute> attributes = new ArrayList<>();
        Map<Integer, AttributeSet> tempSquareAttributes = new HashMap<>();
        Map<AttributeType, Attribute> tempStorageAttributes = new HashMap<>();

        for (EquipSquare equipSquare : equipSquareMap.values()) {
            List<Attribute> squareAttr = equipSquare.getCurrentAttribute();
            tempSquareAttributes.put(equipSquare.getEquipPosition().getId(), AttributeSet.valueOf(squareAttr));
            attributes.addAll(squareAttr);
        }

        AttributeUtils.accumulateToMap(attributes, tempStorageAttributes);
        storageAttributes = tempStorageAttributes;
        squareAttributes = tempSquareAttributes;
        return attributes;
    }

    /**
     * 从配置文件中加载属性
     */
    public void load() {
        for (EquipSquare square : equipSquareMap.values()) {
            square.refreshAttrs();
        }
    }

    // get and set
    public Map<Integer, EquipSquare> getEquipSquareMap() {
        return equipSquareMap;
    }

    // 获取装备栏最终属性
    public Map<AttributeType, Attribute> getStorageAttributes() {
        return storageAttributes;
    }

    @Override
    public String toString() {
        return "EquipStorage{" + "equipSquareMap=" + '\n' + equipSquareMap + '}';
    }

}
