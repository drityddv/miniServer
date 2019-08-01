package game.world.instance.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import game.world.instance.model.InstanceMapInfo;
import game.world.instance.resource.InstanceResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/30 5:29 PM
 */

@Component
public class InstanceManager {

    private static InstanceManager instance;
    // 副本最大数量
    private int maxInstanceCount;

    @Static
    private Map<Long, InstanceResource> instanceResourceMap;

    private Map<Integer, InstanceMapInfo> mapInfoMap = new HashMap<>();

    private Map<Long, InstanceMapInfo> singleMapInfoMap = new HashMap<>();

    // key - InstanceMapInfo

    public static InstanceManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public void addMapInfo(InstanceMapInfo mapInfo) {
        mapInfoMap.put(mapInfo.getMapId(), mapInfo);
    }

    public InstanceMapInfo getMapInfo(int mapId) {
        return mapInfoMap.get(mapId);
    }

    public Map<Integer, InstanceMapInfo> getMapInfoMap() {
        return mapInfoMap;
    }

    public int getMaxInstanceCount() {
        return maxInstanceCount;
    }

    public void setMaxInstanceCount(int count) {
        maxInstanceCount = count;
    }

    // 副本不在时 创建副本
    public void loadMapInfo(int mapId) {}

    public void destroy(int mapId) {
        mapInfoMap.remove(mapId);
    }

    public InstanceResource getInstanceResource(long configId) {
        return instanceResourceMap.get(configId);
    }

    public InstanceResource getInstanceResourceByMapId(int mapId) {
        return instanceResourceMap.values().stream().filter(instanceResource -> instanceResource.getMapId() == mapId)
            .findFirst().get();
    }
}
