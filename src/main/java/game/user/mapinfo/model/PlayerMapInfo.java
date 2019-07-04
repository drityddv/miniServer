package game.user.mapinfo.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.miniMap.handler.MapGroupType;
import game.world.AbstractMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/2 下午5:15
 */

public class PlayerMapInfo {
    /**
     * 地图组id-玩家地图数据
     */
    private Map<Integer, AbstractMapInfo> infoMap = new ConcurrentHashMap<>();

    public static PlayerMapInfo valueOf() {
        PlayerMapInfo mapInfo = new PlayerMapInfo();
        return mapInfo;
    }

    public Map<Integer, AbstractMapInfo> getInfoMap() {
        return infoMap;
    }

    public AbstractMapInfo getOrCreateMapInfo(MapGroupType type) {
        int groupId = type.getGroupId();
        AbstractMapInfo mapInfo = infoMap.get(groupId);
        if (mapInfo == null) {
            mapInfo = type.initAndCreateMapInfo();
            infoMap.put(groupId, mapInfo);
        }
        return mapInfo;
    }
}
