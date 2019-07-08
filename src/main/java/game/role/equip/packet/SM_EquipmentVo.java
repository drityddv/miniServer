package game.role.equip.packet;

import java.util.List;

import game.base.game.attribute.Attribute;
import game.role.equip.model.Equipment;

/**
 * id :145
 *
 * @author : ddv
 * @since : 2019/7/1 下午9:53
 */

public class SM_EquipmentVo {

    private Equipment equipment;
    private List<Attribute> attributes;

    public static SM_EquipmentVo valueOf(Equipment equipment, List<Attribute> attributes) {
        SM_EquipmentVo sm = new SM_EquipmentVo();
        sm.equipment = equipment;
        sm.attributes = attributes;
        return sm;
    }

    @Override
    public String toString() {
        return "SM_EquipmentVo{" + "equipment=" + equipment + ", attributes=" + attributes + '}';
    }
}
