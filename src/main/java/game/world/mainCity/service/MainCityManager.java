package game.world.mainCity.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.world.mainCity.model.MainCityMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/10 下午3:54
 */
@Component
public class MainCityManager {
    private static final Logger logger = LoggerFactory.getLogger(MainCityManager.class);

    /**
     * 中立地图通用数据 地图id→地图数据
     */
    private Map<Integer, MainCityMapInfo> mainCityInfoMap = new HashMap<>();

    public void addMainCityMapInfo(MainCityMapInfo mainCityMapInfo) {
        mainCityInfoMap.put(mainCityMapInfo.getMapId(), mainCityMapInfo);
    }

    public MainCityMapInfo getMainCityMapInfo(int mapId) {
        MainCityMapInfo mainCityMapInfo = mainCityInfoMap.get(mapId);
        if (mainCityMapInfo == null) {
            logger.warn("主城地图[{}]不存在!", mapId);
        }
        return mainCityMapInfo;
    }

    public Map<Integer, MainCityMapInfo> getMainCityInfoMap() {
        return mainCityInfoMap;
    }
}
