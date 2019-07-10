package game.role.equip.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.action.IMessageAction;
import game.role.equip.model.EquipSquare;
import game.role.equip.model.EquipStorage;
import game.role.equip.model.Equipment;

/**
 * id: 141
 *
 * @author : ddv
 * @since : 2019/7/1 上午10:22
 */

public class SM_EquipStorage implements IMessageAction {

    private static final Logger logger = LoggerFactory.getLogger("client");

    private EquipStorage equipStorage;

    public static SM_EquipStorage valueOf(EquipStorage equipStorage) {
        SM_EquipStorage sm = new SM_EquipStorage();
        sm.equipStorage = equipStorage;
        return sm;
    }

    @Override
    public void action() {
        equipStorage.getEquipSquareMap().forEach((integer, equipSquare) -> {
            logger.info("装备栏位置[{}] 孔位等级[{}] 是否存在装备[{}] 装备id[{}]", integer, equipSquare.getLevel(),
                !equipSquare.isEmpty(), getEquipmentId(equipSquare));
        });
    }

    private long getEquipmentId(EquipSquare square) {
        Equipment equipment = square.getEquipment();
        if (equipment == null) {
            return 0;
        }
        return equipment.getConfigId();
    }

    @Override
    public String toString() {
        return "SM_EquipStorage{" + "equipStorage=" + equipStorage + '}';
    }
}
