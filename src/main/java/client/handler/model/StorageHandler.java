package client.handler.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.handler.IHandler;
import game.role.equip.model.EquipStorage;
import game.role.equip.packet.SM_EquipStorage;

/**
 * @author : ddv
 * @since : 2019/7/10 下午10:38
 */

public class StorageHandler implements IHandler<SM_EquipStorage> {

    private static Logger logger = LoggerFactory.getLogger(StorageHandler.class);

    @Override
    public void handler(SM_EquipStorage sm_equipStorage) {
        EquipStorage equipStorage = sm_equipStorage.getEquipStorage();
        equipStorage.getEquipSquareMap().forEach((integer, equipSquare) -> {
            logger.info("装备栏位置[{}] 孔位等级[{}] 是否存在装备[{}] 装备id[{}]", integer, equipSquare.getLevel(),
                !equipSquare.isEmpty(), sm_equipStorage.getEquipmentId(equipSquare));
        });
    }
}
