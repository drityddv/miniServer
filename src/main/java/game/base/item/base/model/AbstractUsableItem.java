package game.base.item.base.model;

import game.base.message.exception.RequestException;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/25 下午4:29
 */

public abstract class AbstractUsableItem extends AbstractItem {

    /**
     * 使用条件是否满足
     *
     * @param player
     * @param num
     * @return
     * @throws RequestException
     */
    public abstract void verifyCanUseThrow(Player player, int num) throws RequestException;

    /**
     * 使用条件是否满足
     *
     * @param player
     * @param num
     * @return
     */
    public abstract boolean verifyCanUse(Player player, int num);

    /**
     * 使用效果
     *
     * @param player
     * @param num
     */
    public abstract void useEffect(Player player, int num);
}
