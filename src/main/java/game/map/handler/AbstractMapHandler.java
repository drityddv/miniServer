package game.map.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import game.map.base.AbstractPlayerMapInfo;
import game.map.constant.MapGroupType;
import game.role.player.model.Player;
import game.user.mapinfo.entity.MapInfoEnt;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;

/**
 * 地图处理器
 *
 * @author : ddv
 * @since : 2019/7/2 下午9:22
 */

public abstract class AbstractMapHandler implements IMapHandler {

    private static final Map<Integer, AbstractMapHandler> HANDLER_MAP = new HashMap<>();

    /**
     * 根据地图组id获得处理器 如果没有处理器就用保底地图的
     *
     * @param groupId
     * @param <T>
     * @return
     */
    public static <T extends AbstractMapHandler> T getHandler(int groupId) {
        AbstractMapHandler handler = HANDLER_MAP.get(groupId);
        if (handler == null) {
            handler = HANDLER_MAP.get(MapGroupType.MAIN_CITY.getGroupId());
        }
        return (T)handler;
    }

    /**
     * 获取地图对应的handler
     *
     * @param mapId
     * @param <T>
     * @return
     */
    public static <T extends AbstractMapHandler> T getAbstractMapHandler(int mapId) {
        MiniMapResource miniMapResource = WorldManager.getInstance().getMapResource(mapId);
        return AbstractMapHandler.getHandler(miniMapResource.getGroupId());
    }

    @PostConstruct
    private void init() {
        HANDLER_MAP.put(getGroupType().getGroupId(), this);
    }

    @Override
    public <T extends AbstractPlayerMapInfo> T getMapInfo(Player player, MapInfoEnt ent) {
        return (T)ent.getPlayerMapInfo().getOrCreateMapInfo(getGroupType());
    }

    public boolean canEnterMap(Player player, int mapId, boolean clientRequest) {
        return true;
    }

    public void canEnterMapThrow(Player player, int mapId, boolean clientRequest) {

    }

    /**
     * 进图前置工作[修改玩家信息:玩家切图状态,玩家上一张地图id]
     *
     * @param player
     */
    public final void enterMapPre(Player player) {
        player.setChangingMap(true);
    }

    /**
     * 进入地图后置[修改玩家信息:玩家切图状态,玩家当前地图id]
     *
     * @param player
     * @param currentMapId
     */
    public final void enterMapAfter(Player player, int currentMapId) {
        player.setCurrentMapId(currentMapId);
        player.setChangingMap(false);
    }

    /**
     * 退图前置[修改玩家信息:玩家切图状态]
     *
     * @param player
     */
    public final void leaveMapPre(Player player) {
        player.setChangingMap(true);
    }

    /**
     * 退图后置[修改玩家信息:玩家切图状态,玩家当前地图id,玩家上一张地图id]
     *
     * @param player
     */
    public final void leaveMapAfter(Player player) {
        player.setLastMapId(player.getCurrentMapId());
        player.setCurrentMapId(0);
        player.setChangingMap(false);
    }

    /**
     * 打印地图信息
     *
     * @param player
     * @param mapId
     */
    public abstract void doLogMap(Player player, int mapId);

}
