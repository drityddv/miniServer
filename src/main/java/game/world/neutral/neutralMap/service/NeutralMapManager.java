package game.world.neutral.neutralMap.service;

import java.util.HashMap;
import java.util.Map;

import game.world.neutral.neutralMap.model.NeutralMapInfo;
import org.springframework.stereotype.Component;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:40
 */
@Component
public class NeutralMapManager {
    /**
     * 中立地图通用数据 地图id→地图数据
     */
    private Map<Integer, NeutralMapInfo> commonInfoMap = new HashMap<>();

    public void addNeutralMapCommonInfo(NeutralMapInfo commonInfo) {
        commonInfoMap.put(commonInfo.getMapId(), commonInfo);
    }

    public NeutralMapInfo getNeutralMapCommonInfo(int mapId) {
        return commonInfoMap.get(mapId);
    }

    public Map<Integer, NeutralMapInfo> getCommonInfoMap() {
        return commonInfoMap;
    }
}
