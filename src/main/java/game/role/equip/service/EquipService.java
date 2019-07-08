package game.role.equip.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.condition.AbstractConditionProcessor;
import game.base.consumer.AbstractConsumeProcessor;
import game.base.game.attribute.id.AttributeIdEnum;
import game.common.I18N;
import game.common.exception.RequestException;
import game.role.equip.constant.EquipPosition;
import game.role.equip.entity.EquipStorageEnt;
import game.role.equip.model.EquipSquare;
import game.role.equip.model.EquipStorage;
import game.role.equip.model.Equipment;
import game.role.equip.packet.SM_EquipStorage;
import game.role.equip.packet.SM_EquipmentVo;
import game.role.equip.recource.EquipResource;
import game.role.equip.recource.EquipSquareEnhanceResource;
import game.role.player.model.Player;
import game.user.item.base.model.AbstractItem;
import game.user.pack.service.IPackService;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * 普通装备栏
 *
 * @author : ddv
 * @since : 2019/6/28 下午2:20
 */
@Component
public class EquipService implements IEquipService {

    private static final Logger logger = LoggerFactory.getLogger(EquipService.class);

    @Autowired
    private EquipManager equipManager;

    // 强化装备孔位
    @Override
    public void enhance(Player player, int position) {
        EquipPosition equipPosition = EquipPosition.getPosition(position);
        EquipStorageEnt ent = getEquipStorageEnt(player);
        EquipStorage equipStorage = ent.getEquipStorage();

        EquipSquare equipSquare = equipStorage.getEquipSquare(equipPosition);
        EquipSquareEnhanceResource resource = equipManager.getEquipEnhanceResource(equipSquare.getConfigId());

        if (resource.getNextLevel() == 0) {
            logger.warn("玩家[{}]强化槽位[{}]失败,槽位已经满级", player.getAccountId(), position);
            return;
        }

        // 强化消耗逻辑
        List<AbstractConsumeProcessor> processors = resource.getProcessors();
        for (AbstractConsumeProcessor processor : processors) {
            try {
                processor.doConsume(player);
            } catch (RequestException e) {
                logger.warn("玩家[{}]强化槽位[{}]失败,消耗条件不足", player.getAccountId(), position);
                throw e;
            }
        }

        // 修改孔位信息
        EquipSquareEnhanceResource newEnhanceResource =
            equipManager.getEquipEnhanceResource(resource.getNextLevelConfigId());
        equipSquare.enhance(newEnhanceResource);

        equipManager.save(ent);
        equipStorage.reComputeTargetSquare(player, equipPosition);
        sendStorageInfo(player);
    }

    // 穿装备
    @Override
    public void equip(Player player, long itemConfigId, int position) {
        EquipPosition equipPosition = EquipPosition.getPosition(position);
        IPackService packService = SpringContext.getPackService();
        AbstractItem item = packService.getItemFromPack(player, itemConfigId);
        if (item == null) {
            logger.warn("玩家[{}]穿戴装备失败,道具[{}]不存在于背包中", player.getAccountId(), itemConfigId);
            return;
        }

        // 是否是装备类型的道具
        if (item instanceof Equipment) {
            Equipment equipment = (Equipment)item;
            EquipStorageEnt ent = getEquipStorageEnt(player);
            EquipStorage equipStorage = ent.getEquipStorage();

            // 穿戴条件检查
            EquipResource equipResource = equipManager.getEquipResource(equipment.getConfigId());
            for (AbstractConditionProcessor processor : equipResource.getConditionProcessors()) {
                boolean result = processor.doVerify(player);
                if (!result) {
                    logger.warn("玩家[{}]穿戴装备失败,穿戴要求不满足", player.getAccountId());
                    RequestException.throwException(I18N.EQUIP_WEAR_CONDITION_NOT_QUALIFIED);
                }
            }

            if (equipment.getEquipPosition() != equipPosition) {
                logger.warn("玩家[{}]穿戴装备失败,装备栏类型[{}]和装备不匹配", player.getAccountId(), equipPosition.getId());
                return;
            }

            // 装备栏上不允许有装备
            boolean empty = equipStorage.isSquareEmpty(equipPosition);
            if (!empty) {
                logger.warn("玩家[{}]穿戴装备失败,装备栏[{}]上存在装备", player.getAccountId(), equipPosition.getId());
                return;
            }
            packService.reduceItem(player, item, 1);
            equipStorage.addEquip(equipment, equipPosition);
            // 计算装备栏属性变动
            equipStorage.reComputeTargetSquare(player, equipPosition);

            // 不要忘记保存了啊啊啊!!
            equipManager.save(ent);
            sendStorageInfo(player);
        } else {
            logger.warn("玩家[{}]穿戴装备失败,道具[{}]不是装备类型", player.getAccountId(), item.getConfigId());
        }
    }

    @Override
    public void unDress(Player player, int position) {
        EquipPosition equipPosition = EquipPosition.getPosition(position);
        EquipStorageEnt ent = getEquipStorageEnt(player);
        EquipStorage equipStorage = ent.getEquipStorage();
        EquipSquare equipSquare = equipStorage.getEquipSquare(equipPosition);
        Equipment equipment = equipSquare.getEquipment();

        if (equipment == null) {
            logger.warn("玩家[{}]脱卸装备失败,装备栏[{}]无装备", player.getAccountId(), position);
            return;
        }

        equipment.add(1);
        SpringContext.getPackService().addItem(player, equipment);
        equipSquare.unDressEquip();
        equipManager.save(ent);

        equipStorage.reComputeTargetSquare(player, equipPosition);
        sendStorageInfo(player);
    }

    @Override
    public EquipResource getEquipResource(long itemConfigId) {
        return equipManager.getEquipResource(itemConfigId);
    }

    @Override
    public EquipSquareEnhanceResource getEquipEnhanceResource(long configId) {
        return equipManager.getEquipEnhanceResource(configId);
    }

    @Override
    public EquipStorage getEquipStorage(Player player) {
        return equipManager.loadOrCreate(player.getPlayerId()).getEquipStorage();
    }

    private EquipStorageEnt getEquipStorageEnt(Player player) {
        return equipManager.loadOrCreate(player.getPlayerId());
    }

    @Override
    public void loadEquipStorage(Player player) {
        EquipStorage equipStorage = getEquipStorage(player);
        equipStorage.load();
        player.getAttributeContainer().putAttributes(AttributeIdEnum.BASE_EQUIPMENT,
            equipStorage.reComputeSquareAttrs());
    }

    @Override
    public void requestEquipStorage(Player player) {
        sendStorageInfo(player);
    }

    @Override
    public void requestEquipment(Player player, int position) {
        EquipPosition equipPosition = EquipPosition.getPosition(position);
        EquipSquare equipSquare = getEquipStorage(player).getEquipSquare(equipPosition);
        Equipment equipment = equipSquare.getEquipment();
        EquipResource equipResource = getEquipResource(equipment.getConfigId());
        PacketUtil.send(player, SM_EquipmentVo.valueOf(equipment, equipResource.getAttributes()));
    }

    private void sendStorageInfo(Player player) {
        EquipStorage equipStorage = getEquipStorage(player);
        PacketUtil.send(player, SM_EquipStorage.valueOf(equipStorage));
    }
}
