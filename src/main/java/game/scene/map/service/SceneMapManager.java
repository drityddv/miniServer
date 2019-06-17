package game.scene.map.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.map.IMap;
import game.common.Ii8n;
import game.common.exception.RequestException;
import middleware.anno.Manager;
import middleware.resource.IManager;
import middleware.resource.storage.Storage;
import middleware.resource.storage.StorageManager;

/**
 * 地图配置先在这里写死,等待后续增加静态资源功能后修改 1: 新手村
 *
 * @author : ddv
 * @since : 2019/5/7 上午10:34
 */
@Component
@Manager
public class SceneMapManager implements IManager {

    @Autowired
    private StorageManager storageManager;

    private Map<Long, IMap> sceneMaps = new ConcurrentHashMap<>();

    // 对应玩家-存在地图状态表
    private Map<Long, Long> playerMaps = new ConcurrentHashMap<>();

    public IMap getMapByMapId(long mapId) {
        IMap map = sceneMaps.get(mapId);

        if (map == null) {
            RequestException.throwException(Ii8n.MAP_NOT_EXIST);
        }
        return map;
    }

    public void addMap(long mapId, IMap map) {
        sceneMaps.put(mapId, map);
    }

    public Map<Long, IMap> getSceneMaps() {
        return sceneMaps;
    }

    @Override
    public void initManager() {
        Storage<Long, IMap> storage = (Storage<Long, IMap>)storageManager.getStorageMap().get(IMap.class);

        Map<Long, IMap> storageData = storage.getStorageMap();
        storageData.forEach((aLong, iMap) -> {
            sceneMaps.put(aLong, iMap);
        });

        Map<Long, IMap> tempMap = new HashMap<>();

        sceneMaps.forEach((aLong, iMap) -> {
            tempMap.put(iMap.getCurrentMapId(), iMap);
        });

        sceneMaps = tempMap;
    }

    public boolean existInMap(long playerId) {
        return playerMaps.containsKey(playerId);
    }

    public Map<Long, Long> getPlayerMaps() {
        return playerMaps;
    }

    public Long getPlayerInMap(long playerId) {
        return playerMaps.get(playerId);
    }

}
