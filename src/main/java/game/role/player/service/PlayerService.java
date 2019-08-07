package game.role.player.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.id.AttributeIdEnum;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.manager.SessionManager;
import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.publicsystem.rank.model.type.LevelRankInfo;
import game.role.player.entity.PlayerEnt;
import game.role.player.model.Player;
import game.role.player.resource.PlayerResource;
import game.user.login.event.PlayerLoadSynEvent;
import game.world.fight.syncStrategy.impl.LevelSynStrategy;
import net.model.USession;
import spring.SpringContext;
import utils.SessionUtil;

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
        player.fighterSync(LevelSynStrategy.valueOf(player));
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

    @Override
    public Player loadPlayer(String accountId) {
        PlayerEnt playerEnt = getPlayerWithoutCreate(accountId);
        if (playerEnt == null) {
            return null;
        }
        Player player = playerEnt.getPlayer();
        if (!player.isLoaded()) {
            synchronized (playerEnt) {
                if (!player.isLoaded()) {
                    player.setLoaded(true);
                    loadPlayer(player);
                }
            }
        }

        return player;
    }

    private void loadPlayer(Player player) {
        SpringContext.getEventBus().pushEventSyn(PlayerLoadSynEvent.valueOf(player));
        player.getAttributeContainer().containerRecompute();
    }

    @Override
    public PlayerEnt getPlayerWithoutCreate(String accountId) {
        return playerManager.load(accountId);
    }

    @Override
    public void hotFixCorrect(Player player, String resourceName) {
        if (!resourceName.equals("PlayerResource")) {
            return;
        }
        loadPlayerAttribute(player);
        player.getAttributeContainer().containerRecompute();

    }

    @Override
    public void createPlayer(USession session, int sex) {
        if (sex < 0 || sex > 1) {
            RequestException.throwException(I18N.SEX_ERROR);
        }
        String accountId = SessionUtil.getAccountIdFromSession(session);
        PlayerEnt playerEnt = PlayerEnt.valueOf(accountId);
        playerEnt.getPlayer().setSex(sex);
        savePlayer(playerEnt);
    }

    @Override
    public boolean isPlayerOnline(String accountId) {
        return SessionManager.isPlayerOnline(accountId);

    }

    @Override
    public void save(Player player) {
        playerManager.saveEntity(getPlayerEnt(player));
    }

    @Override
    public void handlerLevelUp(Player player) {
        SpringContext.getRankService().addRankInfo(LevelRankInfo.valueOf(player.getAccountId(), player.getLevel()));
    }

    @Override
    public Player getPlayer(long playerId) {
        return playerManager.loadPlayerById(playerId);
    }

}
