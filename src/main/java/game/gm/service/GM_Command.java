package game.gm.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.fight.model.pvpunit.FighterAccount;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.id.AttributeId;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.game.attribute.util.AttributeUtils;
import game.gm.event.HotFixEvent;
import game.gm.packet.SM_LogMessage;
import game.user.equip.constant.EquipPosition;
import game.user.equip.model.EquipStorage;
import game.user.equip.model.Equipment;
import game.user.equip.service.EquipService;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import game.user.pack.model.Pack;
import game.user.pack.service.IPackService;
import game.user.player.model.Player;
import game.user.player.service.IPlayerService;
import net.utils.PacketUtil;
import spring.SpringContext;
import utils.ClassUtil;
import utils.StringUtil;

/**
 * gm命令后台实现
 *
 * @author : ddv
 * @since : 2019/5/7 下午3:01
 */

@Component
public class GM_Command {

    private static final Logger logger = LoggerFactory.getLogger(GM_Command.class);

    @Autowired
    private IPackService packService;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private EquipService equipService;

    public void shutdown(Player player){
		SpringContext.getServer().shutdown();
    	SpringContext.getSceneExecutorService().shutdown();
    	SpringContext.getAccountExecutorService().shutdown();

	}

    public void logPlayer(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.wipePlaceholder("打印玩家[{}]属性", player.getAccountId()));
        sb.append(StringUtil.wipePlaceholder("账号id[{}]", player.getAccountId()));
        sb.append(StringUtil.wipePlaceholder("playerId[{}]", player.getPlayerId()));
        sb.append(StringUtil.wipePlaceholder("等级[{}]", player.getLevel()));
        sb.append(StringUtil.wipePlaceholder("黄金[{}]", player.getGold()));

        PacketUtil.send(player, SM_LogMessage.valueOf(sb.toString()));
    }

    public void logAttrs(Player player) {
        StringBuilder sb = new StringBuilder();
        PlayerAttributeContainer attributeContainer = player.getAttributeContainer();
        Map<AttributeId, AttributeSet> modelAttributeSet = attributeContainer.getModelAttributeSet();
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        sb.append(StringUtil.wipePlaceholder("打印玩家[{}]属性", player.getAccountId()));
        sb.append(StringUtil.wipePlaceholder("玩家共有[{}]个属性模块", modelAttributeSet.size()));
        AttributeUtils.logAttrs(attributeContainer, sb);
        String message = sb.toString();
        logger.info('\n' + message);

        PacketUtil.send(player, SM_LogMessage.valueOf(message));
    }

    public void levelUp(Player player) {
        playerService.playerLevelUp(player);
    }

    public void logPack(Player player) {
        StringBuilder sb = new StringBuilder();
        Pack pack = player.getPack();
        sb.append(StringUtil.wipePlaceholder("打印背包,背包总容量[{}]", pack.getSize()));
        pack.getPackSquares().stream().filter(packSquare -> !packSquare.isEmpty()).forEach(packSquare -> {
            AbstractItem item = packSquare.getItem();
            ItemResource resource = item.getResource();
            sb.append(StringUtil.wipePlaceholder("物品[{}] 名称[{}] 数量[{}]", resource.getConfigId(), resource.getItemName(),
                packSquare.getItem()));
        });
        PacketUtil.send(player, SM_LogMessage.valueOf(sb.toString()));
    }

    public void logEquipStorage(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("打印玩家装备栏\n");
        EquipStorage equipStorage = player.getEquipStorage();
        equipStorage.getEquipSquareMap().forEach((integer, equipSquare) -> {
            Equipment equipment = equipSquare.getEquipment();
            if (equipment == null) {
                sb.append(StringUtil.wipePlaceholder("装备栏位置[{}] 装备[{}]", EquipPosition.getPosition(integer), "无"));
            } else {
                sb.append(StringUtil.wipePlaceholder("装备栏位置[{}] 装备[{}]", EquipPosition.getPosition(integer),
                    equipment.getResource().getItemName()));
            }
        });

        PacketUtil.send(player, SM_LogMessage.valueOf(sb.toString()));
    }

    public void addItem(Player player, long configId, int num) {
        packService.addItem(player, packService.createItem(configId), num);
    }

    public void pushHotFix(Player player, String resourceName) {
        SpringContext.getEventBus().pushEventSyn(HotFixEvent.valueOf(player, resourceName));
    }

    public void addFightAccount(Player player) {
        SpringContext.getNeutralMapService().pkPre(player);
    }

    public void run(Player player) {
        FighterAccount fighterAccount = SpringContext.getFightService().initForPlayer(player);
        FighterAccount bean = ClassUtil.getBean(fighterAccount, FighterAccount.class);
    }
}
