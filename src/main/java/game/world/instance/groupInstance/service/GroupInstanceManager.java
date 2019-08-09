package game.world.instance.groupInstance.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import game.world.instance.groupInstance.model.GroupInstanceMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/30 5:29 PM
 */

@Component
public class GroupInstanceManager {

    private static final int MAX_PLAYER_SIZE = 100;

    private Map<Integer, GroupInstanceMapInfo> mapInfoMap = new HashMap<>();

    public static int getMaxPlayerSize() {
        return MAX_PLAYER_SIZE;
    }

    public void addMapInfo(GroupInstanceMapInfo mapInfo) {
        mapInfoMap.put(mapInfo.getMapId(), mapInfo);
    }

    public GroupInstanceMapInfo getMapInfo(int mapId) {
        return mapInfoMap.get(mapId);
    }

    public Map<Integer, GroupInstanceMapInfo> getMapInfoMap() {
        return mapInfoMap;
    }

    public void removeMapInfo(int mapId) {
        mapInfoMap.remove(mapId);
    }
}
