package game.role.equip.packet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.base.game.attribute.Attribute;
import game.role.equip.model.Equipment;

/**
 * id :145
 *
 * @author : ddv
 * @since : 2019/7/1 下午9:53
 */

public class SM_EquipmentVo {

    private static final Logger logger = LoggerFactory.getLogger("client");

    private Equipment equipment;
    private List<Attribute> attributes;

    public static SM_EquipmentVo valueOf(Equipment equipment, List<Attribute> attributes) {
        SM_EquipmentVo sm = new SM_EquipmentVo();
        sm.equipment = equipment;
        sm.attributes = attributes;
        return sm;
    }

    @Action
    private void action() {
        logger.info("装备id[{}] 装备位置[{}]", equipment.getConfigId(), equipment.getEquipPosition());
        attributes.forEach(attribute -> {
            logger.info("属性信息 [{}] [{}]", attribute.getAttributeType().getTypeName(), attribute.getValue());
        });
    }

    @Override
    public String toString() {
        return "SM_EquipmentVo{" + "equipment=" + equipment + ", attributes=" + attributes + '}';
    }
}
