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
     * 添加道具
     *
     * @param player
     * @param item
     */
    void addItemWithThrow(Player player, AbstractItem item);

    /**
     * 添加道具
     *
     * @param player
     * @param item
     * @return
     */
    boolean addItem(Player player, AbstractItem item);

    /**
     * 扣除道具
     *
     * @param player
     * @param item
     *            注意这个引用的数量会被改变
     * @return
     */
    boolean reduceItem(Player player, AbstractItem item);

    /**
     * 扣除道具
     *
     * @param player
     * @param item
     *            注意这个引用的数量会被改变
     */
    void reduceItemWithThrow(Player player, AbstractItem item);

    /**
     * 扣除道具
     *
     * @param player
     * @param itemConfigId
     * @param num
     */
    void reduceItemWithThrow(Player player, long itemConfigId, int num);

    /**
     * 扣除道具
     *
     * @param player
     * @param itemConfigId
     * @param num
     * @return
     */
    boolean reduceItem(Player player, long itemConfigId, int num);

    /**
     * 使用道具
     *
     * @param player
     * @param itemResource
     * @param count
     */
    void useItem(Player player, ItemResource itemResource, int count);

    /**
     * 背包格子是否已满 如果需要指定item检查容量请调用isEnoughSize
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
     * 指定数量物品是否可以塞进背包
     *
     * @param player
     * @param itemConfigId
     * @param itemNum
     * @return
     */
    boolean isEnoughSize(Player player, long itemConfigId, int itemNum);

    /**
     * 获取背包中指定道具的数量
     *
     * @param player
     * @param item
     * @return
     */
    int getItemNum(Player player, AbstractItem item);

    /**
     * 获取背包的道具 返回背包内道具的引用
     *
     * @param player
     * @param configId
     * @return
     */
    AbstractItem getItemFromPack(Player player, long configId);

    /**
     * 背包整理
     *
     * @param player
     */
    void sortPack(Player player);
}
