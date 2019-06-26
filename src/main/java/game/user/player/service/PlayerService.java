package game.user.player.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.player.PlayerModel;
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

        // 写死测试一下
        Attribute attribute = new Attribute();
        attribute.setModel(PlayerModel.PLAYER_SELF);

        attribute.setModelValue(new HashMap<>(16));
        Map<AttributeType, Long> modelValue = attribute.getModelValue();

        for (AttributeType type : AttributeType.values()) {
            modelValue.put(type, 100L);
        }

        player.getAttributeContainer().reCompute(attribute);
    }

    @Override
    public void addException(Player player, long exception) {

    }

    @Override
    public void playerLevelUp(Player player) {
        PlayerResource playerResource = playerManager.getPlayerResource(player.getLevel());
        player.getAttributeContainer().reCompute(playerResource.getAttribute());
    }

    @Override
    public void savePlayer(Player player) {
        playerManager.saveEntity(playerManager.loadOrCreate(player.getAccountId()));
    }

}
