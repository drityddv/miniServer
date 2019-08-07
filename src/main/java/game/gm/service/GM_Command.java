package game.gm.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.util.ExecutorUtils;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.id.AttributeId;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.game.attribute.util.AttributeUtils;
import game.base.item.base.model.AbstractItem;
import game.base.item.resource.ItemResource;
import game.base.item.service.IItemService;
import game.common.service.ICommonService;
import game.gm.event.HotFixEvent;
import game.gm.packet.SM_LogMessage;
import game.publicsystem.alliance.model.ServerAllianceInfo;
import game.publicsystem.rank.model.ServerRank;
import game.publicsystem.rank.model.type.BattleScoreRankInfo;
import game.publicsystem.rank.model.type.LevelRankInfo;
import game.role.equip.constant.EquipPosition;
import game.role.equip.model.EquipStorage;
import game.role.equip.model.Equipment;
import game.role.equip.service.EquipService;
import game.role.player.entity.PlayerEnt;
import game.role.player.model.Player;
import game.role.player.service.IPlayerService;
import game.role.skill.model.SkillEntry;
import game.role.skill.model.SkillList;
import game.role.skill.service.SkillManager;
import game.user.login.entity.UserEnt;
import game.user.login.service.LoginManager;
import game.user.pack.model.Pack;
import game.user.pack.service.IPackService;
import game.world.base.command.scene.TestMapCommand;
import game.world.utils.MapUtil;
import net.utils.PacketUtil;
import spring.SpringContext;
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
    private LoginManager loginManager;

    @Autowired
    private IPackService packService;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private EquipService equipService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private IItemService iItemService;

    @Autowired
    private SkillManager skillManager;

    public void logPlayer(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.wipePlaceholder("打印玩家[{}]属性", player.getAccountId()));
        sb.append(StringUtil.wipePlaceholder("账号id[{}]", player.getAccountId()));
        sb.append(StringUtil.wipePlaceholder("公会id[{}]", player.getPlayerAllianceInfo().getAllianceId()));
        sb.append(StringUtil.wipePlaceholder("playerId[{}]", player.getPlayerId()));
        sb.append(StringUtil.wipePlaceholder("性别[{}]", player.getSex()));
        sb.append(StringUtil.wipePlaceholder("战力[{}]", player.getBattleScore()));
        sb.append(StringUtil.wipePlaceholder("等级[{}]", player.getLevel()));
        sb.append(StringUtil.wipePlaceholder("黄金[{}]", player.getGold()));
        sb.append(StringUtil.wipePlaceholder("技能点[{}]", player.getSkillPoint()));

        PacketUtil.send(player, SM_LogMessage.valueOf(sb.toString()));
    }

    public void logAttrs(Player player) {
        StringBuilder sb = new StringBuilder();
        PlayerAttributeContainer attributeContainer = player.getAttributeContainer();
        Map<AttributeId, AttributeSet> modelAttributeSet = attributeContainer.getModelAttributeSet();
        sb.append(StringUtil.wipePlaceholder("打印玩家[{}]属性", player.getAccountId()));
        sb.append(StringUtil.wipePlaceholder("玩家共有[{}]个属性模块", modelAttributeSet.size()));
        AttributeUtils.logAttrs(attributeContainer, sb);
        String message = sb.toString();
        logger.info('\n' + message);

        PacketUtil.send(player, SM_LogMessage.valueOf(message));
    }

    public void logSkill(Player player) {
        StringBuffer sb = new StringBuffer();
        MapUtil.logPlayerSkill(player.getSkillList(), sb);
        logger.info("\n", sb.toString());
        PacketUtil.send(player, SM_LogMessage.valueOf(sb.toString()));
    }

    private void logSkills(Player player) {
        StringBuffer sb = new StringBuffer();
        SkillList skillList = player.getSkillList();
        sb.append(StringUtil.wipePlaceholder("默认技能栏[{}]", skillList.getDefaultSquareIndex()));
        skillList.getSkills().forEach((skillId, skillEntry) -> {
            sb.append(StringUtil.wipePlaceholder("技能id[{}] 技能等级[{}] hashcode[{}]", skillId, skillEntry.getLevel(),
                skillEntry.hashCode()));
        });

        skillList.getSkillSquareMap().forEach((index, skillSquare) -> {
            sb.append(StringUtil.wipePlaceholder("技能栏[{}]", index));
            for (SkillEntry skillEntry : skillSquare.getSquareSkills().values()) {
                sb.append(StringUtil.wipePlaceholder("	技能id[{}] 技能等级[{}] hashcode[{}]", skillEntry.getSkillId(),
                    skillEntry.getLevel(), skillEntry.hashCode()));
            }
        });
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
            sb.append(StringUtil.wipePlaceholder("物品[{}] 名称[{}] 详情[{}]", resource.getConfigId(), resource.getItemName(),
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

    /**
     * 打印装备栏的属性数据
     *
     * @param player
     */
    public void logEquipStorageAttrs(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("打印玩家装备栏属性数据\n");
        EquipStorage equipStorage = player.getEquipStorage();
        equipStorage.getEquipSquareMap().forEach((integer, equipSquare) -> {
            sb.append(StringUtil.wipePlaceholder("位置[{}]", integer));
            sb.append(StringUtil.wipePlaceholder("孔位属性\n"));
            AttributeUtils.logAttrs(equipSquare.getLocalSquareAttrs(), sb);
            sb.append(StringUtil.wipePlaceholder("装备属性\n"));
            AttributeUtils.logAttrs(equipSquare.getEquipAttrs(), sb);
            sb.append(StringUtil.wipePlaceholder("最终内部属性\n"));
            AttributeUtils.logAttrs(equipSquare.getFinalAttrs(), sb);
        });
        sb.append(StringUtil.wipePlaceholder("装备栏最终属性\n"));
        AttributeUtils.logAttrs(equipStorage.getStorageAttributes(), sb);
        String message = sb.toString();
        PacketUtil.send(player, SM_LogMessage.valueOf(message));
    }

    public void setLevel(Player player, int level) {
        while (level > player.getLevel()) {
            playerService.playerLevelUp(player);
        }

    }

    public void addItem(Player player, long configId, int num) {
        packService.addItem(player, iItemService.createItem(configId, num));
    }

    public void reduceItem(Player player, long configId, int num) {
        boolean success = packService.reduceItem(player, iItemService.createItem(configId, num));
    }

    public void pushHotFix(Player player, String resourceName) {
        SpringContext.getEventBus().pushEventSyn(HotFixEvent.valueOf(player, resourceName));
    }

    public void sortPack(Player player) {
        SpringContext.getPackService().sortPack(player);
    }

    public void test(Player player, String accountId) {
        boolean online = SpringContext.getPlayerService().isPlayerOnline(accountId);
    }

    public void setSkillPoint(Player player, int skillPoint) {
        PlayerEnt playerEnt = SpringContext.getPlayerService().getPlayerEnt(player);
        player.setSkillPoint(skillPoint);
        playerService.savePlayer(playerEnt);
    }

    public void getAlliance(Player player) {
        ServerAllianceInfo allianceInfo = SpringContext.getAllianceService().getAllianceInfo(player);
    }

    public void run(Player player) {
        ServerRank serverRankInfo = SpringContext.getRankService().getServerRankInfo(player);
    }

    public void mapTest(Player player, int mapId, int x, int y, int radius) {
        Map<String, Object> param = new HashMap<>();
        param.put("x", x);
        param.put("y", y);
        param.put("radius", radius);
        TestMapCommand command = TestMapCommand.valueOf(mapId, 0, param);
        ExecutorUtils.submit(command);
    }

    public void resetPlayerAllianceId(Player player, long allianceId) {
        player.getPlayerAllianceInfo().changeAllianceId(allianceId, true);
        playerService.save(player);
    }

    public void mockRankData(Player player) {
        SpringContext.getRankService().addRankInfo(new LevelRankInfo("1", 1));
        SpringContext.getRankService().addRankInfo(new LevelRankInfo("2", 2));
        SpringContext.getRankService().addRankInfo(new LevelRankInfo("3", 3));
        SpringContext.getRankService().addRankInfo(new LevelRankInfo("4", 4));
        SpringContext.getRankService().addRankInfoCallback(player, new BattleScoreRankInfo("ddv", 9999));
        SpringContext.getRankService().addRankInfoCallback(player, new LevelRankInfo("ddv", 5));
    }

    public void rankTest(Player player) {
        BattleScoreRankInfo rankInfo = BattleScoreRankInfo.valueOf(player);
        SpringContext.getRankService().addRankInfo(rankInfo);
    }

    public void mockUsers(Player player, int count) {
        for (int i = 0; i < count; i++) {
            String k = i + "";
            loginManager.save(UserEnt.valueOf(k, k, k, k, k));
        }
    }

    public void serverRank(Player player){
		ServerRank serverRankInfo = SpringContext.getRankService().getServerRankInfo(player);
	}

}
