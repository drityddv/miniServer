package game.role.equip.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.role.equip.model.EquipSquare;
import game.role.equip.model.EquipStorage;
import game.role.equip.model.Equipment;

/**
 * id: 141
 *
 * @author : ddv
 * @since : 2019/7/1 上午10:22
 */
public class SM_EquipStorage {

    private static final Logger logger = LoggerFactory.getLogger("client");

    private EquipStorage equipStorage;

    public static SM_EquipStorage valueOf(EquipStorage equipStorage) {
        SM_EquipStorage sm = new SM_EquipStorage();
        sm.equipStorage = equipStorage;
        return sm;
    }

    public long getEquipmentId(EquipSquare square) {
        Equipment equipment = square.getEquipment();
        if (equipment == null) {
            return 0;
        }
        return equipment.getConfigId();
    }

    public EquipStorage getEquipStorage() {
        return equipStorage;
    }
}
