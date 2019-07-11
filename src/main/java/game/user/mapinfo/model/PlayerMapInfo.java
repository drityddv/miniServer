package game.user.mapinfo.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.map.base.AbstractPlayerMapInfo;
import game.map.constant.MapGroupType;

/**
 * @author : ddv
 * @since : 2019/7/2 下午5:15
 */

public class PlayerMapInfo {
    /**
     * 地图组id-玩家地图数据
     */
    private Map<Integer, AbstractPlayerMapInfo> infoMap = new ConcurrentHashMap<>();

    public static PlayerMapInfo valueOf() {
        PlayerMapInfo mapInfo = new PlayerMapInfo();
        return mapInfo;
    }

    public Map<Integer, AbstractPlayerMapInfo> getInfoMap() {
        return infoMap;
    }

    public AbstractPlayerMapInfo getOrCreateMapInfo(MapGroupType type) {
        int groupId = type.getGroupId();
        AbstractPlayerMapInfo mapInfo = infoMap.get(groupId);
        if (mapInfo == null) {
            mapInfo = type.initAndCreateMapInfo();
            infoMap.put(groupId, mapInfo);
        }
        return mapInfo;
    }
}
