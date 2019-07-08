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
    // 孔位的单独属性 不要直接使用 请通过service访问资源文件
    private transient Map<AttributeType, Attribute> squareAttrs = new HashMap<>();

    public EquipSquare() {}

    public static EquipSquare valueOf(EquipPosition equipPosition) {
        EquipSquareEnhanceResource equipEnhanceResource =
            SpringContext.getEquipService().getEquipEnhanceResource(equipPosition.getConfigId());
        EquipSquare square = new EquipSquare();
        square.equipPosition = equipPosition;
        square.level = equipEnhanceResource.getLevel();
        square.configId = equipEnhanceResource.getConfigId();
        square.squareAttrs = equipEnhanceResource.getAttributeMap();
        return square;
    }

    // 直接访问资源文件的属性
    public Map<AttributeType, Attribute> getSquareAttrs() {
        EquipSquareEnhanceResource resource =
            SpringContext.getEquipService().getEquipEnhanceResource(equipPosition.getConfigId());
        return resource.getAttributeMap();
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
        // 更新孔位属性
        squareAttrs = getSquareAttrs();

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

    // get and set
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

    public Map<AttributeType, Attribute> getEquipAttrs() {
        return equipAttrs;
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
