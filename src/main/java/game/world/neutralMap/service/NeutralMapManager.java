package game.world.neutralMap.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import game.world.neutralMap.model.NeutralMapCommonInfo;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:40
 */
@Component
public class NeutralMapManager {
    /**
     * 中立地图通用数据 地图id→地图数据
     */
    private Map<Integer, NeutralMapCommonInfo> commonInfoMap = new HashMap<>();

    public void addNeutralMapCommonInfo(NeutralMapCommonInfo commonInfo) {
        commonInfoMap.put(commonInfo.getMapId(), commonInfo);
    }

    public NeutralMapCommonInfo getNeutralMapCommonInfo(int mapId) {
        return commonInfoMap.get(mapId);
    }

    public Map<Integer, NeutralMapCommonInfo> getCommonInfoMap() {
        return commonInfoMap;
    }
}
