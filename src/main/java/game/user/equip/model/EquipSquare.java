package game.user.equip.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.util.AttributeUtils;
import game.user.equip.constant.EquipPosition;
import game.user.equip.recource.EquipSquareEnhanceResource;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/6/28 下午12:01
 */

public class EquipSquare {

    private EquipPosition equipPosition;

    private Equipment equipment;

    private int level;
    private long configId;

    // 合并后的属性
    private transient Map<AttributeType, Attribute> finalAttrs = new HashMap<>();
    // 装备的单独属性
    private transient Map<AttributeType, Attribute> equipAttrs = new HashMap<>();
    // 孔位的单独属性
    private transient Map<AttributeType, Attribute> squareAttrs = new HashMap<>();

    public static EquipSquare valueOf(EquipPosition equipPosition) {
        EquipSquareEnhanceResource equipEnhanceResource =
            SpringContext.getEquipService().getEquipEnhanceResource(equipPosition.getConfigId());
        EquipSquare square = new EquipSquare();
        square.equipPosition = equipPosition;
        square.level = equipEnhanceResource.getLevel();
        square.configId = equipEnhanceResource.getConfigId();
        AttributeUtils.accumulateToMap(equipEnhanceResource.getAttributes(), square.squareAttrs);
        return square;
    }

    public boolean isEmpty() {
        return equipment == null;
    }

    public List<Attribute> getCurrentAttribute() {
        List<Attribute> attributes = new ArrayList<>();
        if (equipment == null) {
            return attributes;
        }
        AttributeUtils.accumulateToList(finalAttrs, attributes);
        return attributes;
    }

    public void reSetAttrs() {
        // 更新装备属性
        if (equipment != null) {
            List<Attribute> equipmentAttrs = equipment.getAttributeList();
            Map<AttributeType, Attribute> newEquipAttrs = new HashMap<>();
            AttributeUtils.accumulateToMap(equipmentAttrs, newEquipAttrs);
            equipAttrs = newEquipAttrs;
        }

        // 计算合并后的属性
        Map<AttributeType, Attribute> newFinalAttrs = new HashMap<>();
        AttributeUtils.accumulateToMap(equipAttrs, newFinalAttrs);
        AttributeUtils.accumulateToMap(squareAttrs, newFinalAttrs);
        finalAttrs = newFinalAttrs;
    }

    public void unDressEquip() {
        equipment = null;
        equipAttrs.clear();
        reSetAttrs();
    }

    public void enhance(EquipSquareEnhanceResource resource) {
        this.configId = resource.getConfigId();
        this.level = resource.getLevel();
        Map<AttributeType, Attribute> newSquareAttrs = new HashMap<>();
        AttributeUtils.accumulateToMap(resource.getAttributes(), newSquareAttrs);
        this.squareAttrs = newSquareAttrs;
    }

    /// get and set

    public EquipPosition getEquipPosition() {
        return equipPosition;
    }

    public void setEquipPosition(EquipPosition equipPosition) {
        this.equipPosition = equipPosition;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<AttributeType, Attribute> getFinalAttrs() {
        return finalAttrs;
    }

    public void setFinalAttrs(Map<AttributeType, Attribute> finalAttrs) {
        this.finalAttrs = finalAttrs;
    }

    public Map<AttributeType, Attribute> getEquipAttrs() {
        return equipAttrs;
    }

    public void setEquipAttrs(Map<AttributeType, Attribute> equipAttrs) {
        this.equipAttrs = equipAttrs;
    }

    public Map<AttributeType, Attribute> getSquareAttrs() {
        return squareAttrs;
    }

    public void setSquareAttrs(Map<AttributeType, Attribute> squareAttrs) {
        this.squareAttrs = squareAttrs;
    }

    public long getConfigId() {
        return configId;
    }

    @Override
    public String toString() {
        return "EquipSquare{" + "equipPosition=" + equipPosition + ", equipment=" + equipment + ", level=" + level + '}'
            + '\n';
    }

}
