package game.world.instance.base.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import game.world.instance.base.resource.InstanceResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/8/1 12:28 PM
 */
@Component
public class InstanceManager {

    private static InstanceManager instance;

    @Static
    private Map<Long, InstanceResource> instanceResourceMap;

    public static InstanceManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public InstanceResource getInstanceResource(long configId) {
        return instanceResourceMap.get(configId);
    }

    public InstanceResource getInstanceResourceByMapId(int mapId) {
        return instanceResourceMap.values().stream().filter(instanceResource -> instanceResource.getMapId() == mapId)
            .findFirst().get();
    }
}
