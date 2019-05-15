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

}
