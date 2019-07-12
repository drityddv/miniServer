package game.base.consume;

import game.base.message.exception.RequestException;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/11 8:47 PM
 */

public interface IConsume {

    /**
     * 检查
     *
     * @param player
     * @throws RequestException
     */
    void verifyThrow(Player player) throws RequestException;

    /**
     * 检查
     *
     * @param player
     * @return
     */
    boolean verify(Player player);

    /**
     * 消耗
     *
     * @param player
     */
    void consume(Player player);
}
