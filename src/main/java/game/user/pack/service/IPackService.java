package game.user.pack.service;

import game.role.player.model.Player;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import game.user.pack.model.Pack;

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
     * @param clientRequest
     * @return
     */
    Pack getPlayerPack(Player player, boolean clientRequest);

    /**
     * 添加道具至背包
     *
     * @param player
     * @param item
     */
    void addItem(Player player, AbstractItem item);

    /**
     * 使用道具
     *
     * @param player
     * @param itemResource
     * @param count
     */
    void useItem(Player player, ItemResource itemResource, int count);

    /**
     * 获取资源
     *
     * @param configId
     * @return
     */
    ItemResource getResource(Long configId);

    /**
     * 背包是否已满 如果需要指定item检查容量请调用isEnoughSize
     *
     * @param player
     * @return
     */
    boolean isFull(Player player);

    /**
     * 指定数量物品是否可以塞进背包
     *
     * @param player
     * @param item
     * @return
     */
    boolean isEnoughSize(Player player, AbstractItem item);

    /**
     * 获取背包中指定道具的数量
     *
     * @param player
     * @param item
     * @return
     */
    int getItemNum(Player player, AbstractItem item);

    /**
     * 背包减少道具并且下发状态
     *
     * @param player
     * @param item
     * @param num
     */
    void reduceItem(Player player, AbstractItem item, int num);

    /**
     * 获取背包的道具
     *
     * @param player
     * @param configId
     * @return
     */
    AbstractItem getItemFromPack(Player player, long configId);
}
