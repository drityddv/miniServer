package game.world.base.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import game.world.base.resource.MiniMapResource;
import middleware.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/2 下午8:52
 */
@Component
public class WorldManager {

    private static WorldManager instance;
    @Static
    private Map<Integer, MiniMapResource> mapResources;

    public static WorldManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public MiniMapResource getMapResource(int mapId) {
        return mapResources.get(mapId);
    }

    public List<MiniMapResource> getMapResourcesByGroup(int groupId) {
        List<MiniMapResource> resources;
        resources = mapResources.values().stream().filter(miniMapResource -> miniMapResource.getGroupId() == groupId)
            .collect(Collectors.toList());
        return resources;
    }

}
