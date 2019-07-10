package game.map.handler;

import game.map.model.Grid;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:00
 */

public interface IMovableMapHandler {

    /**
     * 移动
     *
     * @param player
     * @param targetGrid
     */
    void move(Player player, Grid targetGrid);
}
