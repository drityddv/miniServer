package game.world.neutral.neutralMap.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.world.neutral.neutralMap.model.NeutralMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:40
 */
@Component
public class NeutralMapManager {
    private static final Logger logger = LoggerFactory.getLogger(NeutralMapManager.class);
    /**
     * 中立地图通用数据 地图id→地图数据
     */
    private Map<Integer, NeutralMapInfo> commonInfoMap = new HashMap<>();

    public void addNeutralMapCommonInfo(NeutralMapInfo commonInfo) {
        commonInfoMap.put(commonInfo.getMapId(), commonInfo);
    }

    public NeutralMapInfo getNeutralMapCommonInfo(int mapId) {

        NeutralMapInfo neutralMapInfo = commonInfoMap.get(mapId);
        if (neutralMapInfo == null) {
            logger.warn("中立地图[{}]不存在!", mapId);
        }
        return neutralMapInfo;

    }

    public Map<Integer, NeutralMapInfo> getCommonInfoMap() {
        return commonInfoMap;
    }
}
