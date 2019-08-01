package game.world.instance.base.model;

import game.map.base.BaseMapInfo;
import game.world.instance.base.resource.InstanceResource;

/**
 * @author : ddv
 * @since : 2019/8/1 2:41 PM
 */

public abstract class InstanceMapInfo extends BaseMapInfo<BaseInstanceMapScene> {
    // 副本信息
    protected InstanceResource instanceResource;

    public InstanceResource getInstanceResource() {
        return instanceResource;
    }
}
