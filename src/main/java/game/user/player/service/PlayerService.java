package game.user.player.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.id.AttributeIdEnum;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.user.player.model.Player;
import game.user.player.resource.PlayerResource;

/**
 * @author : ddv
 * @since : 2019/5/15 上午10:05
 */
@Component
public class PlayerService implements IPlayerService {

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
        PlayerResource playerResource = playerManager.getPlayerResource(player.getLevel());
        // player.getAttributeContainer().reCompute(playerResource.getAttributeList());
    }

    @Override
    public void savePlayer(Player player) {
        playerManager.saveEntity(playerManager.loadOrCreate(player.getAccountId()));
    }

    @Override
    public PlayerResource getResource(int id) {
        return playerManager.getPlayerResource(id);
    }

}
