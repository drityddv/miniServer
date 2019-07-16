package game.base.effect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.effect.resource.EffectResource;

/**
 * @author : ddv
 * @since : 2019/7/15 4:44 PM
 */
@Component
public class EffectService implements IEffectService {

    @Autowired
    private EffectManager effectManager;

    @Override
    public EffectResource getEffectResource(long configId) {
        return effectManager.getEffectResource(configId);
    }
}
