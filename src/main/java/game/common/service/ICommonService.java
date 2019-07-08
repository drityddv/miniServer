package game.common.service;

import game.user.item.base.model.AbstractItem;

/**
 * 公共服务[创建道具]
 *
 * @author : ddv
 * @since : 2019/7/8 下午9:01
 */

public interface ICommonService {

    /**
     * 创建道具
     *
     * @param player
     * @param configId
     * @param num
     * @return
     */
    AbstractItem createItem(long configId, int num);
}
