package game.base.effect.model.analysis;

import game.base.effect.resource.EffectResource;

/**
 * @author : ddv
 * @since : 2019/7/15 5:09 PM
 */

public interface IBuffAnalysis {

    /**
     * 解析属性参数
     *
     * @param effectResource
     */
    void doParse(EffectResource effectResource);
}
