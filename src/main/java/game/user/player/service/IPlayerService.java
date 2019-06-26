package game.user.player.service;

import game.user.player.model.Player;

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
     * 玩家升级
     *
     * @param player
     */
    void playerLevelUp(Player player);

    /**
     * 保存玩家信息
     *
     * @param player
     */
    void savePlayer(Player player);
}
