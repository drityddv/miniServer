package game.world.mainCity.service;

import game.map.base.AbstractMovableScene;
import game.map.base.AbstractScene;
import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.mainCity.model.MainCitySceneScene;

/**
 * @author : ddv
 * @since : 2019/7/10 下午3:31
 */

public interface IMainCityService {
    /**
     * 初始化
     */
    void init();

    /**
     * 进入地图
     *
     * @param player
     * @param mapId
     */
    void enterMap(Player player, int mapId);

    /**
     * 离开地图
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
    MainCitySceneScene getCurrentScene(Player player);

    /**
     * 打印地图
     *
     * @param player
     * @param mapId
     */
    void doLogMap(Player player, int mapId);

    /**
     * 获取场景
     *
     * @param mapId
     * @return
     */
	AbstractMovableScene getMapScene(int mapId);
}
