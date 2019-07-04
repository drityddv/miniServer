package game.user.player.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.id.AttributeIdEnum;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.user.player.entity.PlayerEnt;
import game.user.player.model.Player;
import game.user.player.resource.PlayerResource;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:05
 */
@Component
public class PlayerService implements IPlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerManager playerManager;

    @Override
    public Player getPlayerByAccountId(String accountId) {
        return playerManager.loadOrCreate(accountId).getPlayer();
    }

    // 加载玩家自身基本属性
    @Override
    public void loadPlayerAttribute(Player player) {
        PlayerResource playerResource = playerManager.getPlayerResource(player.getLevel());
        List<Attribute> attributeList = playerResource.getAttributeList();

        PlayerAttributeContainer playerAttributeContainer = player.getAttributeContainer();
        playerAttributeContainer.putAttributes(AttributeIdEnum.BASE, attributeList, null);
    }

    @Override
    public void addException(Player player, long exception) {
        System.out.println(1);
    }

    @Override
    public void playerLevelUp(Player player) {
        PlayerEnt playerEnt = player.getPlayerEnt();
        PlayerResource playerResource = playerManager.getPlayerResource(player.getLevel());
        if (playerResource.getNextLevel() == 0) {
            logger.warn("玩家[{}]升级失败,玩家已经到达最大等级[{}]", player.getAccountId(), player.getLevel());
            return;
        }
        PlayerResource nextLevelPlayerResource = playerManager.getPlayerResource(playerResource.getNextLevel());
        player.setLevel(playerResource.getNextLevel());
        player.getAttributeContainer().putAttributesWithRecompute(AttributeIdEnum.BASE,
            nextLevelPlayerResource.getAttributeList(), true);
        player.getAttributeContainer().containerRecompute();

        savePlayer(playerEnt);
    }

    @Override
    public void savePlayer(PlayerEnt playerEnt) {
        playerManager.saveEntity(playerEnt);
    }

    @Override
    public PlayerResource getResource(int id) {
        return playerManager.getPlayerResource(id);
    }

    @Override
    public PlayerEnt getPlayerEnt(Player player) {
        return playerManager.loadOrCreate(player.getAccountId());
    }

}
