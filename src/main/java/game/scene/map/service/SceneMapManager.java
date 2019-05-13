package game.scene.map.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.map.IMap;
import game.common.Ii8n;
import game.common.exception.RequestException;
import game.scene.map.resource.MapResource;
import middleware.anno.Manager;
import middleware.resource.IManager;
import middleware.resource.storage.StorageLong;
import middleware.resource.storage.StorageManager;
import spring.SpringContext;

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

    @PostConstruct
    public void init() {
        // IMap noviceVillage = NoviceVillage.valueOf(1L, 6, 6);
        // // StorageLong<IMap> storage =
        // // (StorageLong<IMap>)SpringContext.getStorageManager().getStorageMap().get(MapResource.class);
        // sceneMaps.put(noviceVillage.getCurrentMapId(), noviceVillage);
        // // sceneMaps.put(storage.get(1L).getCurrentMapId(), storage.get(1L));
        // sceneMaps.put(2L, StormWind.valueOf(2, 9, 9));
    }

    public IMap getMapByMapId(long mapId) {
        IMap map = sceneMaps.get(mapId);

        if (map == null) {
            RequestException.throwException(Ii8n.MAP_NOT_EXIST);
        }
        return map;
    }

    public Map<Long, IMap> getSceneMaps() {
        return sceneMaps;
    }

    @Override
    public void initManager() {
        StorageLong<IMap> storage =
            (StorageLong<IMap>)SpringContext.getStorageManager().getStorageMap().get(MapResource.class);
        sceneMaps.put(storage.get(1L).getCurrentMapId(), storage.get(1L));
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
