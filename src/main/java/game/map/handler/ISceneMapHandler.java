package game.map.handler;

import game.map.base.AbstractScene;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:07
 */

public interface ISceneMapHandler<T extends AbstractScene> extends IMapHandler {

	/**
     * 获得玩家当前所存在的场景
     *
     * @param player
     * @return
     */
    T getCurrentScene(Player player);
}
