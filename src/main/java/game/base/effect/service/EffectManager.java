package game.base.effect.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import game.base.effect.resource.EffectResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/15 4:45 PM
 */
@Component
public class EffectManager {

    private static EffectManager instance;
    @Static
    private static Map<Long, EffectResource> effectResources;

    public static EffectManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public EffectResource getEffectResource(long configId) {
        return effectResources.get(configId);
    }

    public EffectResource getEffectResourceByEffectId(long typeId) {
        return effectResources.values().stream().filter(effectResource -> effectResource.getEffectTypeId() == typeId)
            .findAny().get();
    }
}
