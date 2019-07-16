package game.base.effect.service;

import game.base.effect.resource.EffectResource;

/**
 * @author : ddv
 * @since : 2019/7/15 4:44 PM
 */

public interface IEffectService {

    /**
     * 获取效果资源文件
     *
     * @param configId
     * @return
     */
    EffectResource getEffectResource(long configId);
}
