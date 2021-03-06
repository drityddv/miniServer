package game.role.equip.service;

import game.role.equip.model.EquipStorage;
import game.role.equip.recource.EquipResource;
import game.role.equip.recource.EquipSquareEnhanceResource;
import game.role.player.model.Player;

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
     * 替换装备 不允许[任何一方为null]
     *
     * @param player
     * @param equipConfigId
     * @param position
     */
    void switchEquip(Player player, int equipConfigId, int position);

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

    /**
     * 客户端请求发包装备栏数据
     *
     * @param player
     */
    void requestEquipStorage(Player player);

    /**
     * 客户端请求装备栏的装备数据
     *
     * @param player
     * @param position
     */
    void requestEquipment(Player player, int position);
}
