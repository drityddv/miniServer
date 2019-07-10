package game.map.handler;

import game.map.base.AbstractScene;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:07
 */

public interface ISceneMapHandler<T extends AbstractScene> extends IMapHandler {

    /**
     * 选择场景
     *
     * @param accountId
     * @param mapId
     * @return
     */
    T getEnterScene(String accountId, int mapId);

    /**
     * 获得玩家当前所存在的场景
     *
     * @param accountId
     * @param mapId
     * @return
     */
    T getCurrentScene(String accountId, int mapId);
}
