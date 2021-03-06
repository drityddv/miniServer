package game.world.neutral.neutralMap.service;

import java.util.Map;

import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.role.player.model.Player;
import game.world.neutral.neutralMap.model.NeutralMapScene;

/**
 * 中立地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午5:29
 */

public interface INeutralMapService {
    /**
     * 初始化
     */
    void init();

    /**
     * 是否可以进入地图
     *
     * @param player
     * @param mapId
     * @return
     */
    boolean canEnterMap(Player player, int mapId);

    /**
     * 进入地图
     *
     * @param player
     * @param mapId
     */
    void enterMap(Player player, int mapId);

    /**
     * 离开地图 不进入新的地图
     *
     * @param player
     */
    void leaveMap(Player player);

    /**
     * 移动
     *
     * @param player
     *
     * @param targetGrid
     */
    void doMove(Player player, Grid targetGrid);

    /**
     * 获取玩家当前所处场景
     *
     * @param player
     * @return
     */
    NeutralMapScene getCurrentScene(Player player);

    /**
     * 打印地图
     *
     * @param player
     * @param mapId
     */
    void logMap(Player player, int mapId);

    /**
     * 地图心跳
     *
     * @param mapId
     */
    void heartBeat(int mapId);

    /**
     * 地图内玩家单位
     *
     * @param mapId
     * @return
     */
    Map<Long, PlayerMapObject> getVisibleObjects(int mapId);

    /**
     * 怪物单位
     *
     * @param mapId
     * @return
     */
    Map<Long, MonsterMapObject> getMonsterObjects(int mapId);

    /**
     * 获得场景
     *
     * @param mapId
     * @return
     */
    NeutralMapScene getMapScene(int mapId);

    /**
     * 测试用
     *
     * @param mapId
     */
    void test(int mapId, Map<String, Object> param);

    /**
     * 区域广播
     *
     * @param currentGrid
     */
    void broadcast(int mapId, Grid currentGrid);
}
