package game.role.equip.packet;

/**
 * @author : ddv
 * @since : 2019/7/9 下午2:16
 */

public class CM_SwitchEquip {
    private int equipConfigId;
    private int position;

    public int getEquipConfigId() {
        return equipConfigId;
    }

    public int getPosition() {
        return position;
    }
}
