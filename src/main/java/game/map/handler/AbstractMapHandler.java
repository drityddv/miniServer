package game.map.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import game.map.base.AbstractMapInfo;
import game.map.constant.MapGroupType;
import game.role.player.model.Player;
import game.user.mapinfo.entity.MapInfoEnt;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.neutral.neutralMap.model.NeutralMapInfo;
import spring.SpringContext;

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
            handler = HANDLER_MAP.get(MapGroupType.SAFE_MAP.getGroupId());
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
    public <T extends AbstractMapInfo> T getMapInfo(Player player, MapInfoEnt ent) {
        return (T)ent.getPlayerMapInfo().getOrCreateMapInfo(getGroupType());
    }

    public boolean canEnterMap(Player player, int mapId, boolean clientRequest) {
        return true;
    }

    public void canEnterMapThrow(Player player, int mapId, boolean clientRequest) {

    }

    /**
     * 进图的前置工作 修改mapInfo
     *
     * @param player
     * @param mapId
     */
    public final void enterMapPre(Player player, int mapId) {
        player.setChangingMap(true);
    }

    public final void enterMapAfter(Player player, int currentMapId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(player);
        NeutralMapInfo mapInfo = getMapInfo(player, mapInfoEnt);
        mapInfo.setMapId(currentMapId);
        SpringContext.getMapInfoService().saveMapInfoEnt(player, mapInfoEnt);
        player.setCurrentMapId(currentMapId);
        player.setChangingMap(false);
    }

    public final void leaveMapPre(Player player) {
        player.setChangingMap(true);
    }

    public final void leaveMapAfter(Player player) {
        player.setCurrentMapId(0);
        player.setChangingMap(false);
    }

    public final void logMap(Player player, int mapId) {
        MiniMapResource mapResource = WorldManager.getInstance().getMapResource(mapId);
        if (mapResource == null) {
            return;
        }
        doLogMap(player, mapId);
    }

    /**
     * 打印地图信息
     *
     * @param player
     * @param mapId
     */
    public abstract void doLogMap(Player player, int mapId);

}
