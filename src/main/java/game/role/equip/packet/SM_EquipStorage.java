package game.role.equip.packet;

import game.role.equip.model.EquipStorage;

/**
 * id: 141
 *
 * @author : ddv
 * @since : 2019/7/1 上午10:22
 */

public class SM_EquipStorage {

    private EquipStorage equipStorage;

    public static SM_EquipStorage valueOf(EquipStorage equipStorage) {
        SM_EquipStorage sm = new SM_EquipStorage();
        sm.equipStorage = equipStorage;
        return sm;
    }

    @Override
    public String toString() {
        return "SM_EquipStorage{" + "equipStorage=" + equipStorage + '}';
    }
}
