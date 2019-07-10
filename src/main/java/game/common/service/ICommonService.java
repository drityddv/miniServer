package game.common.service;

import java.util.List;

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
     * 初始化一些公共服务[服务器定时事件等...]
     */
    void initPublicTask();

    /**
     * 0点事件触发
     */
    void oneHourJob();

    /**
     * 关服
     */
    void serverClose();
}
