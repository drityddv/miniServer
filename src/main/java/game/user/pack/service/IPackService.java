package game.user.pack.service;

import game.base.game.item.Item;
import game.user.pack.model.Pack;
import game.user.player.model.Player;

/**
 * 玩家背包service
 *
 * @author : ddv
 * @since : 2019/6/1 上午10:07
 */

public interface IPackService {

    /**
     * 获取玩家背包
     *
     * @param player
     * @return
     */
    Pack getPlayerPack(Player player);

    /**
     * 添加道具至背包
     *
     * @param player
     * @param item
     * @param count
     */
    void addItem(Player player, Item item, int count);

	/**
	 * 使用道具
	 * @param player
	 * @param item
	 * @param count
	 */
    void useItem(Player player,Item item,int count);
}
