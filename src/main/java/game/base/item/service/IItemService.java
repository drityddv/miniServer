package game.base.item.service;

import java.util.List;

import game.base.item.base.model.AbstractItem;
import game.base.item.resource.ItemResource;

/**
 * @author : ddv
 * @since : 2019/7/10 下午7:54
 */

public interface IItemService {

    /**
     * 创建道具
     *
     * @param configId
     * @param num
     * @return
     */
    AbstractItem createItem(long configId, int num);

    /**
     * 创建id不同的道具集合
     *
     * @param configId
     * @param num
     * @return
     */
    List<AbstractItem> createItems(long configId, int num);

    /**
     * 获取资源
     *
     * @param configId
     * @return
     */
    ItemResource getItemResource(Long configId);

    /**
     * 通过掉落表创建道具
     *
     * @param dropConfigId
     * @return
     */
    List<AbstractItem> createItemsByDropConfig(long dropConfigId);
}
