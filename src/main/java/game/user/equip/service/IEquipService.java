package game.user.equip.service;

import game.user.equip.model.EquipStorage;
import game.user.equip.recource.EquipResource;
import game.user.equip.recource.EquipSquareEnhanceResource;
import game.user.player.model.Player;

/**
 * 装备
 *
 * @author : ddv
 * @since : 2019/6/28 下午2:20
 */

public interface IEquipService {

    /**
     * 强化装备栏位
     *
     * @param player
     * @param position
     */
    void enhance(Player player, int position);

    /**
     * 穿戴装备
     *
     * @param player
     * @param equipId
     * @param position
     */
    void equip(Player player, long equipId, int position);

    /**
     * 脱装备
     *
     * @param player
     * @param position
     */
    void unDress(Player player, int position);

    /**
     * 获取装备资源文件
     *
     * @param configId
     * @return
     */
    EquipResource getEquipResource(long configId);

    /**
     * 获取装备栏强化资源文件
     *
     * @param configId
     * @return
     */
    EquipSquareEnhanceResource getEquipEnhanceResource(long configId);

    /**
     * 获取装备栏
     *
     * @param player
     * @return
     */
    EquipStorage getEquipStorage(Player player);

    /**
     * 加载装备事件
     *
     * @param player
     */
    void loadEquipStorage(Player player);

}
