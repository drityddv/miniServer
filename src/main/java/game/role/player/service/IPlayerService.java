package game.role.player.service;

import game.role.player.entity.PlayerEnt;
import game.role.player.model.Player;
import game.role.player.resource.PlayerResource;
import net.model.USession;

/**
 * @author : ddv
 * @since : 2019/5/14 下午8:29
 */

public interface IPlayerService {
    /**
     * 根据账户id获取player
     *
     * @param accountId
     * @return
     */
    Player getPlayerByAccountId(String accountId);

    /**
     * 加载玩家属性容器
     *
     * @param player
     */
    void loadPlayerAttribute(Player player);

    /**
     * 玩家增加经验
     *
     * @param player
     * @param exception
     */
    void addException(Player player, long exception);

    /**
     * 玩家升级并且重新计算属性与同步
     *
     * @param player
     */
    void playerLevelUp(Player player);

    /**
     * 保存玩家信息
     *
     * @param playerEnt
     */
    void savePlayer(PlayerEnt playerEnt);

    /**
     * 获取玩家资源文件
     *
     * @param id
     * @return
     */
    PlayerResource getResource(int id);

    /**
     * 获取玩家信息
     *
     * @param player
     * @return
     */
    PlayerEnt getPlayerEnt(Player player);

    /**
     * 加载玩家信息
     *
     * @param accountId
     * @return
     */
    Player loadPlayer(String accountId);

    /**
     * 获取角色 不会自动创建
     *
     * @param accountId
     * @return
     */
    PlayerEnt getPlayerWithoutCreate(String accountId);

    /**
     * 热更资源属性修正
     *
     * @param player
     * @param resourceName
     */
    void hotFixCorrect(Player player, String resourceName);

    /**
     * 玩家手动创建角色
     *
     * @param session
     * @param sex
     */
    void createPlayer(USession session, int sex);

    // /**
    // * 获取在线玩家
    // *
    // * @param player
    // * @return
    // */
    // Player getOnlinePlayer(Player player);

    /**
     * 玩家是否在线
     *
     * @param accountId
     * @return
     */
    boolean isPlayerOnline(String accountId);

    /**
     * 保存数据
     *
     * @param player
     */
    void save(Player player);

    /**
     * 处理玩家升级事件 [更新排行榜内容]
     *
     * @param player
     */
    void handlerLevelUp(Player player);

    /**
     * 查找玩家
     *
     * @param playerId
     * @return
     */
    Player getPlayer(long playerId);
}
