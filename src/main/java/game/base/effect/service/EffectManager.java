package game.base.effect.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import game.base.effect.resource.EffectResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/15 4:45 PM
 */
@Component
public class EffectManager {

    @Static
    private static Map<Long, EffectResource> effectResources;

    public EffectResource getEffectResource(long configId) {
        return effectResources.get(configId);
    }
}
