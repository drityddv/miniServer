package game.world.instance.singleIntance.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import game.world.instance.singleIntance.model.SingleInstanceMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/30 5:29 PM
 */

@Component
public class SingleInstanceManager {

    private Map<Integer, SingleInstanceMapInfo> mapInfoMap = new HashMap<>();

    public void addMapInfo(SingleInstanceMapInfo mapInfo) {
        mapInfoMap.put(mapInfo.getMapId(), mapInfo);
    }

    public SingleInstanceMapInfo getMapInfo(int mapId) {
        return mapInfoMap.get(mapId);
    }

    public Map<Integer, SingleInstanceMapInfo> getMapInfoMap() {
        return mapInfoMap;
    }

}
